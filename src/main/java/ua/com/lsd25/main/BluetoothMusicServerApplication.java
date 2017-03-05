package ua.com.lsd25.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ua.com.lsd25")
public class BluetoothMusicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BluetoothMusicServerApplication.class, args);
    }
}
