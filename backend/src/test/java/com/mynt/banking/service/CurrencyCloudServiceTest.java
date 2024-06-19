package com.mynt.banking.service;

import com.mynt.banking.entity.CurrencyCloud;
import com.mynt.banking.entity.User;
import com.mynt.banking.repository.CurrencyCloudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CurrencyCloudServiceTest {
    @Mock
    private CurrencyCloudRepository currencyCloudRepository;

    @InjectMocks
    private CurrencyCloudService currencyCloudService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockUser = User.builder()
                .id(1L)
                .forename("John")
                .surname("Doe")
                .email("example@email.com")
                .password("examplePassword")
                .build();
    }

    @Test
    void testCreateCurrencyCloud() {
        CurrencyCloud currencyCloud = CurrencyCloud.builder()
                .user(mockUser)
                .UUID("some-uuid")
                .build();

        when(currencyCloudRepository.save(any(CurrencyCloud.class))).thenReturn(currencyCloud);

        CurrencyCloud savedCurrencyCloud = currencyCloudService.createCurrencyCloud(currencyCloud);

        assertNotNull(savedCurrencyCloud);
        assertEquals("some-uuid", savedCurrencyCloud.getUUID());
        verify(currencyCloudRepository, times(1)).save(currencyCloud);
    }

    @Test
    void testGetCurrencyCloudByUserId() {
        CurrencyCloud currencyCloud = CurrencyCloud.builder()
                .user(mockUser)
                .UUID("some-uuid")
                .build();

        when(currencyCloudRepository.findById(mockUser.getId())).thenReturn(Optional.of(currencyCloud));

        CurrencyCloud foundCurrencyCloud = currencyCloudService.getCurrencyCloudByUserId(mockUser.getId());

        assertNotNull(foundCurrencyCloud);
        assertEquals("some-uuid", foundCurrencyCloud.getUUID());
        verify(currencyCloudRepository, times(1)).findById(mockUser.getId());
    }

    @Test
    void testGetCurrencyCloudByUserIdNotFound() {
        when(currencyCloudRepository.findById(mockUser.getId())).thenReturn(Optional.empty());

        CurrencyCloud foundCurrencyCloud = currencyCloudService.getCurrencyCloudByUserId(mockUser.getId());

        assertNull(foundCurrencyCloud);
        verify(currencyCloudRepository, times(1)).findById(mockUser.getId());
    }

    @Test
    void testDeleteCurrencyCloud() {
        doNothing().when(currencyCloudRepository).deleteById(mockUser.getId());

        currencyCloudService.deleteCurrencyCloud(mockUser.getId());

        verify(currencyCloudRepository, times(1)).deleteById(mockUser.getId());
    }
}
