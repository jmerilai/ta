Feature: User can find product from web service
  As a user
  I want to find camera from web service
  
  @Exercise
  Scenario: 01: Web page opened, user search product and click it for details
	Given user has opened "https://amazon.com"
	When user see search input field
	Then user search product "Nikon"
	When user sort results
	Then user selects the second item and click it open