package com.denmit99.hairbnb.controller;

import com.denmit99.hairbnb.constants.Constants;
import com.denmit99.hairbnb.model.AmenityType;
import com.denmit99.hairbnb.model.Currency;
import com.denmit99.hairbnb.model.PlaceType;
import com.denmit99.hairbnb.model.PropertyType;
import com.denmit99.hairbnb.model.bo.ListingBO;
import com.denmit99.hairbnb.model.dto.ListingSearchRequestDTO;
import com.denmit99.hairbnb.repository.ListingRepository;
import com.denmit99.hairbnb.service.JwtService;
import com.denmit99.hairbnb.service.ListingService;
import com.denmit99.hairbnb.service.TokenInfoService;
import com.denmit99.hairbnb.service.UserService;
import com.denmit99.hairbnb.util.RandomEnumUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ListingController.class)
public class ListingControllerTest {

    private static final String LISTING_URI_PREFIX = "/listings";
    private static final String ROLE_USER = "USER";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenInfoService tokenInfoService;

    @MockBean
    private ListingService listingService;

    @MockBean
    private ListingRepository listingRepository;

    @Test
    @WithMockUser
    void search_ValidRequest_ReturnsOk() throws Exception {
        testSearchRequestDtoValidation(dto -> {
        }, status().isOk());
    }

    @Test
    @WithMockUser
    void search_NegativeMinPrice_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setMinPrice(-RandomUtils.nextLong(1, Long.MAX_VALUE)),
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_NegativeMaxPrice_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setMaxPrice(-RandomUtils.nextLong(1, Long.MAX_VALUE)),
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_MinPriceGreaterThanMaxPrice_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> {
                    dto.setMinPrice(2L);
                    dto.setMaxPrice(1L);
                },
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_PriceSetButNoCurrency_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setCurrency(null),
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_NumberOfBedroomsTooSmall_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setNumberOfBedrooms(-RandomUtils.nextInt(1, Integer.MAX_VALUE)),
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_NumberOfBedroomsTooBig_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setNumberOfBedrooms(Constants.LISTING_MAX_BEDROOMS + 1),
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_NumberOfGuestsTooSmall_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setNumberOfGuests(-RandomUtils.nextInt(1, Integer.MAX_VALUE)),
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_NumberOfGuestsTooBig_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setNumberOfGuests(Constants.LISTING_MAX_MAX_GUESTS + 1),
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_NumberOfBathroomsTooSmall_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setNumberOfBathrooms(-RandomUtils.nextInt(1, Integer.MAX_VALUE)),
                status().isBadRequest());
    }

    @Test
    @WithMockUser
    void search_NumberOfBathroomsTooBig_Returns400() throws Exception {
        testSearchRequestDtoValidation(dto -> dto.setNumberOfBathrooms(Constants.LISTING_MAX_BATHROOMS + 1),
                status().isBadRequest());
    }


    @Test
    @WithMockUser
    void getById_ReturnsOk() throws Exception {
        UUID listingId = UUID.randomUUID();
        when(listingRepository.existsById(listingId)).thenReturn(true);
        var listingBO = ListingBO.builder()
                .amenities(Set.of())
                .bedrooms(List.of())
                .build();
        when(listingService.get(listingId)).thenReturn(listingBO);
        mockMvc.perform(get(LISTING_URI_PREFIX + "/" + listingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(listingService).get(listingId);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getById_NonExisting_Returns4xxCode() throws Exception {
        UUID listingId = UUID.randomUUID();
        when(listingRepository.existsById(listingId)).thenReturn(false);
        mockMvc.perform(get(LISTING_URI_PREFIX + "/" + listingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void get_Unauthorized() throws Exception {
        mockMvc.perform(get(LISTING_URI_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private void testSearchRequestDtoValidation(Consumer<ListingSearchRequestDTO> dtoModifier,
                                                ResultMatcher resultMatcher) throws Exception {
        ListingSearchRequestDTO dto = createCorrectListingSearchRequestDTO();
        dtoModifier.accept(dto);
        String requestJson = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post(LISTING_URI_PREFIX)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(resultMatcher);
    }

    private ListingSearchRequestDTO createCorrectListingSearchRequestDTO() {
        ListingSearchRequestDTO dto = new ListingSearchRequestDTO();
        dto.setMinPrice(1L);
        dto.setMaxPrice(2L);
        dto.setCurrency(RandomEnumUtils.nextValue(Currency.class));
        dto.setPropertyTypes(List.of(RandomEnumUtils.nextValue(PropertyType.class)));
        dto.setPlaceTypes(List.of(RandomEnumUtils.nextValue(PlaceType.class)));
        dto.setNumberOfBedrooms(2);
        dto.setNumberOfBathrooms(1);
        dto.setNumberOfGuests(2);
        dto.setAmenities(Set.of(RandomEnumUtils.nextValue(AmenityType.class)));
        return dto;
    }
}
