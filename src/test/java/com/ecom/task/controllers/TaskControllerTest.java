package com.ecom.task.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void singleIpRequests() throws Exception {
        // Given
        var successList = new ArrayList<Integer>();
        var blockedList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {

            var status = mockMvc.perform(get("/task")).andReturn().getResponse().getStatus();
            if (status == 200) {
                successList.add(status);
            } else {
                blockedList.add(status);
            }
            Thread.sleep(90);
        }

        // Expected
        assertAll(
                () -> assertEquals(5, successList.size()),
                () -> assertEquals(5, blockedList.size())
        );
    }

    @Test
    public void multiIpRequests() throws Exception {

        for (int i = 0; i < 10; i++) {
            String ip = "192.168.0." + (i % 2 + 1);

            RequestPostProcessor requestPostProcessor = (MockHttpServletRequest request) -> {
                request.setRemoteAddr(ip);
                return request;
            };

            mockMvc.perform(get("/task")
                            .with(requestPostProcessor))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}