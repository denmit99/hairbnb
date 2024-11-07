package com.denmit99.hairbnb.integration;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.model.UserRole;
import com.denmit99.hairbnb.model.UserToken;
import com.denmit99.hairbnb.model.dto.AddressDTO;
import com.denmit99.hairbnb.model.dto.BedroomListingCreateRequestDTO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.model.dto.ListingDTO;
import com.denmit99.hairbnb.model.dto.ListingLightDTO;
import com.denmit99.hairbnb.model.entity.User;
import com.denmit99.hairbnb.repository.BedroomRepository;
import com.denmit99.hairbnb.repository.ListingAmenityRepository;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class ListingIntegrationTest {

    private static final String HOST_LISTING_URI_PREFIX = "/host/listings";
    private static final String LISTING_URI_PREFIX = "/listings";

    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BedroomRepository bedroomRepository;

    @Autowired
    private ListingAmenityRepository listingAmenityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        POSTGRES.start();
    }

    @AfterAll
    static void afterAll() {
        POSTGRES.stop();
    }

    @BeforeEach
    void setUp() {
        listingAmenityRepository.deleteAll();
        bedroomRepository.deleteAll();
        listingRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Tests that listing creation actually creates listing and saves all corresponding entities in db
     */
    @Test
    void createListing() throws Exception {
        simulateHostUser();

        ListingCreateRequestDTO requestDTO = createCorrectListingCreateRequestDTO();
        MvcResult createListingResult = performCreateListing(requestDTO);
        var listingId = objectMapper.readValue(createListingResult.getResponse().getContentAsString(), ListingDTO.class)
                .getId();

        MvcResult getMyListingsResult = performGetHostListings();
        var myListings = objectMapper.readValue(getMyListingsResult.getResponse().getContentAsString(),
                new TypeReference<List<ListingLightDTO>>() {
                });

        MvcResult getListingResult = mockMvc.perform(get(LISTING_URI_PREFIX + "/" + listingId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var listing = objectMapper.readValue(getListingResult.getResponse().getContentAsString(), ListingDTO.class);
        assertEquals(1, listingRepository.findAll().size());
        assertEquals(requestDTO.getBedrooms().size(), bedroomRepository.findAll().size());
        assertEquals(requestDTO.getAmenities().size(), listingAmenityRepository.findAll().size());
        assertEquals(1, myListings.size());
        assertNotNull(listing);
    }

    @Test
    void deleteListing() throws Exception {
        simulateHostUser();
        ListingCreateRequestDTO requestDTO = createCorrectListingCreateRequestDTO();
        MvcResult createListingResult = performCreateListing(requestDTO);
        var listingId = objectMapper.readValue(createListingResult.getResponse().getContentAsString(), ListingDTO.class)
                .getId();
        mockMvc.perform(delete(HOST_LISTING_URI_PREFIX + "/" + listingId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult getMyListingsResult = performGetHostListings();
        var myListings = objectMapper.readValue(getMyListingsResult.getResponse().getContentAsString(),
                new TypeReference<List<ListingLightDTO>>() {
                });
        assertEquals(0, listingRepository.findAll().size());
        assertEquals(0, bedroomRepository.findAll().size());
        assertEquals(0, listingAmenityRepository.findAll().size());
        assertEquals(0, myListings.size());
    }

    private MvcResult performCreateListing(ListingCreateRequestDTO requestDTO) throws Exception {
        return mockMvc.perform(post(HOST_LISTING_URI_PREFIX)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    private MvcResult performGetHostListings() throws Exception {
        return mockMvc.perform(get(HOST_LISTING_URI_PREFIX)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    private void simulateHostUser() {
        User user = new User();
        user.setFirstName(RandomStringUtils.randomAlphabetic(5));
        user.setLastName(RandomStringUtils.randomAlphabetic(5));
        user.setEmail(RandomStringUtils.randomAlphanumeric(5));
        user.setPassword(RandomStringUtils.randomAlphanumeric(10));
        user.setRole(UserRole.HOST);
        user.setId(UUID.randomUUID());
        user.setCreationDate(ZonedDateTime.now());
        user.setLastLoginDate(ZonedDateTime.now());
        userRepository.save(user);

        UserToken userToken = new UserToken();
        userToken.setEmail(user.getEmail());
        userToken.setFirstName(user.getFirstName());
        userToken.setLastName(user.getLastName());
        userToken.setRole(UserRole.HOST);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userToken, null, userToken.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);
    }

    private ListingCreateRequestDTO createCorrectListingCreateRequestDTO() {
        ListingCreateRequestDTO dto = new ListingCreateRequestDTO();
        dto.setTitle(RandomStringUtils.randomAlphanumeric(10));
        dto.setDescription(RandomStringUtils.randomAlphanumeric(10));
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry(RandomStringUtils.randomAlphanumeric(10));
        addressDTO.setStreet(RandomStringUtils.randomAlphanumeric(10));
        addressDTO.setCity(RandomStringUtils.randomAlphanumeric(10));
        addressDTO.setHouseNumber(RandomStringUtils.randomNumeric(3));
        addressDTO.setZipCode(RandomStringUtils.randomNumeric(5));
        dto.setAddress(addressDTO);
        dto.setPricePerNight(20.0);
        dto.setCurrency(Currency.EUR);
        dto.setPropertyType(PropertyType.APARTMENT);
        dto.setPlaceType(PlaceType.ENTIRE_PLACE);
        dto.setMaxGuests(4);
        dto.setBedrooms(List.of(
                BedroomListingCreateRequestDTO.builder()
                        .queenNum(1)
                        .build(),
                BedroomListingCreateRequestDTO.builder()
                        .sofaNum(1)
                        .singleNum(1)
                        .build())
        );
        dto.setNumberOfBathrooms(2);
        dto.setAmenities(Set.of(AmenityType.WI_FI, AmenityType.ESSENTIALS));
        return dto;
    }
}
