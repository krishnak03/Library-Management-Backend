package com.example.library.management.tool.library;

import com.example.library.management.tool.library.util.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootTest
class LibraryApplicationTests {
	Connection conn = null;

	@BeforeEach
	void setUp() throws SQLException {
		System.setProperty("DB_USERNAME", "postgres");
		System.setProperty("DB_PASSWORD", "12345");
		System.setProperty("DB_URL", "jdbc:postgresql://localhost:5432/postgres");
		conn = getConnection();
	}

	public static Connection getConnection() throws SQLException {
		String url = System.getProperty("DB_URL");
		String username = System.getProperty("DB_USERNAME");
		String password = System.getProperty("DB_PASSWORD");


		return DriverManager.getConnection(url, username, password);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testEncryptionLogic() {
		String str = "Kartik";
		String encryptedStr = EncryptionUtil.encrypt(str);
		String decryptedStr = EncryptionUtil.decrypt(encryptedStr);
		Assertions.assertEquals(str, decryptedStr);
	}

}
