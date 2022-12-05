# Country Public Holiday Count Application

### Instructions

1.Run the application with "MyswaggyappApplication class"<br/>

2.On *localhost:8080* call GET method on path "/myswaggyapp/holidaycount/{countryCode}/{years}"<br/>

- countryCode -> path variable for the desired countries holidays<br/>
- years -> year interval for which to count holidays (ex.2000-2001), both years inclusive<br/>
- to get a single years holiday count -> 1995-1995<br/>

### Description

For the project I used the provided API and stored it in the **application.properties** as a variable for testing
purposes.
Using WebClient in order to connect to the API and perform the correct method calls.

Simple data validation is performed using a regex that checks for the year interval to have 4 numbers a "-" symbol after
and 4 more numbers.
If that is not the case a BAD_REQUEST exception is thrown. From the API documentation I also checked that the staring
year is not lower than 1922,
since there would be no response in that case.

A method that performs the GET call on the API is used to create an array of Holiday elements and that is further used
to call in a loop using IntStream. For each element in the IntStream object the GET method is called (since only one
year can be used)
and mapped to the length of the Holiday array. In the end using reduction to sum all the elements together and returning
the value.

***Important*** to note that I tested the app using the *Integration* testing method.
To do the tests I used MockServer in order to generate a response from the API call not actually calling the main API,
but coding the response that it
should give localed int the "test/utils" folder as a json file. That ensured even if the data amount is changed it still
would
perform all the actions as necessary and give the correct response

### Improvements/Additions

- Using the full potential of the API, thus creating more GET methods
- Validate the **countryCode** from the available countries
- Ability to search for the nearest holiday date worldwide