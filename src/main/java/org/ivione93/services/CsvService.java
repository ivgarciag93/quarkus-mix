package org.ivione93.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class CsvService {

  private static final Pattern VALID_STORE_CODE = Pattern.compile("^\\d{1,5}$");

  public List<Integer> processCsv(final InputStream file) {
    List<Integer> storeCodes = new ArrayList<>();
    List<String> errorCodes = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
      reader.lines()
        .map(String::trim)
        .filter(line -> !line.isEmpty())
        .forEach(line -> {
          if (VALID_STORE_CODE.matcher(line).matches()) {
            storeCodes.add(Integer.valueOf(line));
          } else {
            errorCodes.add(line);
          }
        });
    } catch (Exception e) {
      throw new RuntimeException("Error reading csv file");
    }
    if (errorCodes.isEmpty()) {
      return storeCodes;
    } else {
      throw new ClientErrorException(
        errorCodes.toString(),
        Response.Status.BAD_REQUEST.getStatusCode()
      );
    }
  }
}