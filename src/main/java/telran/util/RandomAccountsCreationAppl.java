package telran.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.apache.logging.log4j.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RandomAccountsCreationAppl implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int ACCOUNTS_COUNT = 50;
	private static final int HOURS_FROM = -100;
	private static final int HOURS_TO = 400;
	private static final int CHAR_FROM = 33;
	private static final int CHAR_TO = 126;
	static List<DTO> accounts = new ArrayList<>();
	static String roles[] = {"ADMIN", "STATIST", "USER", "APPL_ADMIN"};
	private static String fileName = "accounts.json";
	private static Logger LOG = LogManager.getLogger(RandomAccountsCreationAppl.class);
	
	public static void main(String[] args) {
		
		if(!new File(fileName).exists()) {
			LOG.info("creating: {} accounts", ACCOUNTS_COUNT);
			createAccounts();
			LOG.info("saving accounts to a file: {}", fileName);
			saveAccounts();
		} else {
			LOG.warn("file: {} already exist", fileName);
		}

	}

	private static void saveAccounts() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(fileName), accounts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createAccounts() {
		IntStream.range(1, ACCOUNTS_COUNT + 1).forEach(i -> {
			accounts.add(new DTO("account_" + i, getPassword(), 
					getExperation(), getRoles()));
		});	
	}

	private static String[] getRoles() {
		int limit = getRandomInt(1, roles.length);	
		String res[] = new String[limit];
		int index[] = {0};
		new Random().ints(0, roles.length).distinct().limit(limit).forEach(i -> {
			res[index[0]++] = roles[i];
		});
		return res;
	}

	private static int getRandomInt(int from, int to) {
		
		return ThreadLocalRandom.current().nextInt(from, to);
	}

	private static String getExperation() {
		LocalDateTime timeNow = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS");
		return timeNow.plusHours(getRandomInt(HOURS_FROM, HOURS_TO)).format(formatter);
	}

	private static String getPassword() {
		char[] res = new char[8];
		IntStream.range(0, 8).forEach(i -> {
			res[i] = (char) getRandomInt(CHAR_FROM, CHAR_TO);
		});
		return String.valueOf(res);
	}


}
