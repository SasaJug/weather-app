# Android WeatherApp

This is an simple Android app for fetching weather data from openweathermap.org.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://SasaJug@bitbucket.org/SasaJug/weather-application.git
```

## Configuration
Go to https://openweathermap.org/api and get your api key.
After that add it to build.gradle (presentation module)
```gradle
buildConfigField("String", "API_KEY", '"your-api-key"')
```

## Build variants
Use the Android Studio *Build Variants* button to choose between **production** and **staging** flavors combined with debug and release build types


## Generating signed APK
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*

## Maintainers
This project is mantained by:
* [Sasa Jugurdzija](http://github.com/sasajug)


## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Run the linter (ruby lint.rb').
5. Push your branch (git push origin my-new-feature)
6. Create a new Pull Request
