package com.denmit99.hairbnb.controller.host;

import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.model.bo.UserBO;
import com.denmit99.hairbnb.model.dto.AddressDTO;
import com.denmit99.hairbnb.model.dto.BedroomDTO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.JwtService;
import com.denmit99.hairbnb.service.ListingService;
import com.denmit99.hairbnb.service.TokenInfoService;
import com.denmit99.hairbnb.service.UserService;
import com.denmit99.hairbnb.service.UserTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(HostListingController.class)
public class HostListingControllerTest {

    private static final String HOST_LISTING_URI_PREFIX = "/host/listings";
    private static final String ROLE_HOST = "HOST";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserTokenService userTokenService;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenInfoService tokenInfoService;

    @MockBean
    private ListingService listingService;

    @MockBean
    private ListingRepository listingRepository;

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_Valid_ReturnsOk() throws Exception {
        testCreateRequestDtoValidation(dto -> {
        }, status().isOk());
    }

    //TODO combine these two annotations into one custom
    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_TitleIsBlank_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.setTitle(""), status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_TitleIsNull_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.setTitle(null), status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_TitleTooLong_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.setTitle(RandomStringUtils.randomAlphanumeric(101)),
                status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_DescriptionIsBlank_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.setDescription(""), status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_DescriptionIsNull_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.setDescription(null), status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_DescriptionTooLong_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.setDescription(RandomStringUtils.randomAlphanumeric(501)),
                status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_AddressIsNull_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.setAddress(null), status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_AddressZipCodeContainsLetters_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.getAddress()
                .setZipCode(RandomStringUtils.randomAlphanumeric(5)), status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_AddressZipCodeTooLong_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.getAddress().setZipCode(RandomStringUtils.randomNumeric(30)),
                status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_PricePerNightTooLow_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> dto.setPricePerNight(0.0), status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void create_GuestsMoreThanBeds_Returns400() throws Exception {
        testCreateRequestDtoValidation(dto -> {
            dto.setMaxGuests(4);
            dto.setBedrooms(List.of(BedroomDTO.builder().singleNum(1).build()));
        }, status().isBadRequest());
    }

    @Test
    void create_Unauthorized_Returns401Code() throws Exception {
        testCreateRequestDtoValidation(dto -> {
        }, status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void delete_NonExisting_Returns4xxCode() throws Exception {
        UUID listingId = UUID.randomUUID();
        when(listingRepository.existsById(listingId)).thenReturn(false);
        mockMvc.perform(delete(HOST_LISTING_URI_PREFIX + "/" + listingId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void delete_ReturnsOk() throws Exception {
        UUID listingId = UUID.randomUUID();
        when(listingRepository.existsById(listingId)).thenReturn(true);
        mockMvc.perform(delete(HOST_LISTING_URI_PREFIX + "/" + listingId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(listingService).delete(listingId);
    }

    @Test
    void delete_Unauthorized_Returns401Code() throws Exception {
        mockMvc.perform(delete(HOST_LISTING_URI_PREFIX + "/" + RandomUtils.nextLong())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {ROLE_HOST})
    void getAll() throws Exception {
        UUID userId = UUID.randomUUID();
        when(userService.getCurrent()).thenReturn(
                UserBO.builder().id(userId).build()
        );
        when(listingService.getAllByUserId(userId)).thenReturn(List.of());

        mockMvc.perform(get(HOST_LISTING_URI_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(listingService).getAllByUserId(userId);
    }

    @Test
    void getAll_Unauthorized_Returns401Code() throws Exception {
        mockMvc.perform(get(HOST_LISTING_URI_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private void testCreateRequestDtoValidation(Consumer<ListingCreateRequestDTO> dtoModifier,
                                                ResultMatcher resultMatcher) throws Exception {
        ListingCreateRequestDTO dto = createCorrectListingCreateRequestDTO();
        dtoModifier.accept(dto);
        String requestJson = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post(HOST_LISTING_URI_PREFIX)
                        .with(csrf())
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultMatcher);
    }

    private ListingCreateRequestDTO createCorrectListingCreateRequestDTO() {
        ListingCreateRequestDTO dto = new ListingCreateRequestDTO();
        dto.setTitle(RandomStringUtils.randomAlphanumeric(10));
        dto.setDescription(RandomStringUtils.randomAlphanumeric(10));
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry(RandomStringUtils.randomAlphanumeric(10));
        addressDTO.setStreet(RandomStringUtils.randomAlphanumeric(10));
        addressDTO.setCity(RandomStringUtils.randomAlphanumeric(10));
        addressDTO.setZipCode(RandomStringUtils.randomNumeric(5));
        dto.setAddress(addressDTO);
        dto.setPricePerNight(20.0);
        dto.setCurrency(Currency.EUR);
        dto.setPropertyType(PropertyType.APARTMENT);
        dto.setPlaceType(PlaceType.ENTIRE_PLACE);
        dto.setMaxGuests(4);
        dto.setBedrooms(List.of(
                BedroomDTO.builder()
                        .queenNum(1)
                        .build(),
                BedroomDTO.builder()
                        .sofaNum(1)
                        .singleNum(1)
                        .build())
        );
        dto.setNumberOfBathrooms(2);
        dto.setAmenities(Set.of(AmenityType.WI_FI, AmenityType.ESSENTIALS));
        return dto;
    }
}
