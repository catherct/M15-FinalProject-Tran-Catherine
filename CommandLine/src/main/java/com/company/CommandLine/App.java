package com.company.CommandLine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.Scanner;

@SpringBootApplication
public class App {

	// to obtain standard USD format for crypto exchange rate (ensures the number always rounds to 2 decimal places)
	private static final DecimalFormat usdFormat = new DecimalFormat("0.00");

	public static void main(String[] args) {  	// HOW TO BREAK OUT OF TRY-CATCH BLOCK?
		SpringApplication.run(App.class, args);

		// call scanner to read user input
		Scanner myScan = new Scanner(System.in);


		try {
			// build menu and display options
			System.out.println("Welcome to the main menu! Please choose the number of an actionable item below:" + "\n"
					+ "===============================================================================" + "\n"
					+ "Press 1 to print the weather of a city." + "\n"
					+ "Press 2 to print the location of the International Space Station (ISS)." + "\n"
					+ "Press 3 to print the weather of the location of the ISS." + "\n"
					+ "Press 4 to print current cryptocurrency prices." + "\n" + "\n"
					+ "RESPOND WITH ANY OTHER KEY TO EXIT.");


			// accept user response in following line
			int userInput = Integer.parseInt(myScan.nextLine());

			while (userInput > 4 || userInput < 1) {
				System.out.println("Please enter a valid number.");

				userInput = Integer.parseInt(myScan.nextLine());
			}

		} catch (NumberFormatException ne) {
			System.out.println("Thank you! Have a nice day! :)");
		}

		int userInput = Integer.parseInt(myScan.nextLine());

			switch (userInput) {

				case 1:
					// code to retrieve Weather in a City
					System.out.println("To print the weather by city, input a city name here: ");

					// user inputs a city
					String cityName = myScan.nextLine();

					WebClient weatherClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?q=" + cityName
							+ "&appid=92f5008d6444dde521a2b983aa90ebd5");

					try {
						Mono<WeatherResponse> weatherNow = weatherClient
								.get()
								.retrieve()
								.bodyToMono(WeatherResponse.class);
						WeatherResponse weatherResponse = weatherNow.share().block();

						if (weatherResponse != null) {
							System.out.println("Printing current weather in " + cityName + "..." + "\n"
									+ "Weather condition id: " + weatherResponse.weather[0].id + "\n"
									+ "Group of weather parameters: " + weatherResponse.weather[0].main + "\n"
									+ "Weather condition within the group: " + weatherResponse.weather[0].description + "\n"
									+ "Weather icon id: " + weatherResponse.weather[0].icon);
						}

					} catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}

					break;

				case 2:
					// code to retrieve ISS Location
					System.out.println("Printing the ISS coordinates, and city and country location...");

					String lat = null;
					String lon = null;

					WebClient issClient = WebClient.create("http://api.open-notify.org/iss-now.json?callback=");

					try {
						Mono<SpaceResponse> space = issClient
								.get()
								.retrieve()
								.bodyToMono(SpaceResponse.class);

						SpaceResponse spaceResponse = space.share().block();
						System.out.println("Longitude: " + spaceResponse.iss_position.longitude);
						System.out.println("Latitude: " + spaceResponse.iss_position.latitude);

						lat = spaceResponse.iss_position.latitude;
						lon = spaceResponse.iss_position.longitude;

					} catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}

					WebClient geoClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat="
							+ lat + "&lon=" + lon + "&appid=92f5008d6444dde521a2b983aa90ebd5");

					try {
						Mono<WeatherResponse> geo = geoClient
								.get()
								.retrieve()
								.bodyToMono(WeatherResponse.class);

						WeatherResponse geoResponse = geo.share().block();
						System.out.println("City: " + geoResponse.name);
						System.out.println("Country: " + geoResponse.sys.country);
						if (geoResponse.sys.country == null) {
							System.out.println("The ISS is not currently in a country.");
						}
					} catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}

					break;

				case 3:
					// code to retrieve Weather at ISS Location
					System.out.println("Printing weather of ISS location...");

					lat = null;
					lon = null;

					issClient = WebClient.create("http://api.open-notify.org/iss-now.json?callback=");

					try {
						Mono<SpaceResponse> space = issClient
								.get()
								.retrieve()
								.bodyToMono(SpaceResponse.class);

						SpaceResponse spaceResponse = space.share().block();
						System.out.println("Longitude: " + spaceResponse.iss_position.longitude);
						System.out.println("Latitude: " + spaceResponse.iss_position.latitude);

						lat = spaceResponse.iss_position.latitude;
						lon = spaceResponse.iss_position.longitude;

					} catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}

					geoClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat="
							+ lat + "&lon=" + lon + "&appid=92f5008d6444dde521a2b983aa90ebd5");

					try {
						Mono<WeatherResponse> geo = geoClient
								.get()
								.retrieve()
								.bodyToMono(WeatherResponse.class);

						WeatherResponse geoResponse = geo.share().block();
						System.out.println("City: " + geoResponse.name);
						System.out.println("Country: " + geoResponse.sys.country);
						if (geoResponse.sys.country == null) {
							System.out.println("The ISS is not currently in a country.");
						}
					} catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}

					weatherClient = WebClient.create("https://api.openweathermap.org/data/2.5/weather?lat="
							+ lat + "&lon=" + lon + "&appid=92f5008d6444dde521a2b983aa90ebd5");

					try {
						Mono<WeatherResponse> weatherNow = weatherClient
								.get()
								.retrieve()
								.bodyToMono(WeatherResponse.class);
						WeatherResponse weatherResponse = weatherNow.share().block();

						if (weatherResponse != null) {
							System.out.println("Printing current weather in ISS location..." + "\n"
									+ "Weather condition id: " + weatherResponse.weather[0].id + "\n"
									+ "Group of weather parameters: " + weatherResponse.weather[0].main + "\n"
									+ "Weather condition within the group: " + weatherResponse.weather[0].description + "\n"
									+ "Weather icon id: " + weatherResponse.weather[0].icon);
						}

					} catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}

					break;

				case 4:
					// code allowing user to retrieve current crypto prices
					System.out.println("To print the price of a cryptocurrency in US dollars (USD), input the symbol of a cryptocurrency (e.g. BIT or ETH) here: ");

					// user inputs a crypto symbol
					String cryptoInput = myScan.nextLine();

					WebClient cryptoClient = WebClient.create("https://rest.coinapi.io/v1/assets/" + cryptoInput + "?apikey=8FD6291E-F66B-411F-ABF8-54A9122C3115");

					try {
						Mono<CryptoResponse[]> crypto = cryptoClient
								.get()
								.retrieve()
								.bodyToMono(CryptoResponse[].class);

						CryptoResponse[] cryptoResponse = crypto.share().block();

						if (cryptoResponse != null) {
							System.out.println("Name: " + cryptoResponse[0].name);
							System.out.println("Symbol: " + cryptoResponse[0].asset_id);
							System.out.println("Current price in USD: " + usdFormat.format(cryptoResponse[0].price_usd));
						}

					} catch (WebClientResponseException we) {
						int statusCode = we.getRawStatusCode();
						if (statusCode >= 400 && statusCode < 500) {
							System.out.println("Client Error");
						} else if (statusCode >= 500 && statusCode < 600) {
							System.out.println("Server Error");
						}
						System.out.println("Message: " + we.getMessage());
					} catch (DataBufferLimitException de) {
						System.out.println("An error occurred: " + de.getMessage());
					}

					break;

				default:
					System.out.println("Thank you! Have a good day! :)"); // code still runs after default
			}
		}
	}