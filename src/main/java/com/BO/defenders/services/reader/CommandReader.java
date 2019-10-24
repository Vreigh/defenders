package com.BO.defenders.services.reader;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.BO.defenders.services.reader.command.Command;
import com.BO.defenders.services.reader.error.CommandParseException;
import com.BO.defenders.services.reader.parser.CommandParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandReader {

  private final CommandParser commandParser;
  private Scanner scanner;

  @PostConstruct
  public void initScanner() {
    scanner = new Scanner(System.in);
  }

  public Command nextCommand() {
    System.out.println("Awaiting new command...");
    while (true) {
      try {
        return commandParser.parse(scanner.nextLine());
      } catch (CommandParseException e) {
        System.out.println(e.getMessage() + ", try again");
      }
    }
  }

}
