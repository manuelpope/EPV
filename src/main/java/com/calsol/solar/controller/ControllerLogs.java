package com.calsol.solar.controller;

import com.calsol.solar.domain.dto.KeyDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

@RestController
@RequestMapping("/v1/logs")
@Slf4j
@Data
public class ControllerLogs {

    @Value("${spring.keyword.logs}")
    private String CONF_VAR_KEY_LOGS;

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getLogsRequest(@RequestBody KeyDto keyword) {

        try {
            assertTittle(keyword);

            File file = new File("./logs/spring-boot-logger.log");

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }


    }

    @DeleteMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteLogsRequest(@RequestBody KeyDto keyword) {

        try {
            assertTittle(keyword);

            File file = new File("./logs/spring-boot-logger.log");

            FileWriter writer = new FileWriter(file);
            writer.write("cleaned logs!");
            writer.close();

            return ResponseEntity.ok().body("Cleared successfully");


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }


    }


    private void assertTittle(KeyDto keyword) throws Exception {

        if (!keyword.getKeyword().equalsIgnoreCase(CONF_VAR_KEY_LOGS)) {
            System.out.println(keyword);
            throw new Exception("Not entitled");
        }

    }
}
