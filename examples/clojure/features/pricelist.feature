# language: en
Feature: Price List
  In order to avoid silly mistakes
  Cashiers must be able to select prices from a price list

  Scenario: Regular numbers
    Given the following price list
      | Name       | Price |
      | Espresso   | 20    |
      | Croissant  | 16    |
    Given I have entered "Espresso" into the calculator
    And I have entered "Croissant" into the calculator
    When I press add
    Then the current value should be 36
