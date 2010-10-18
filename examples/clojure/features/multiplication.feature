# language: en
Feature: Multiplication
  In order to avoid silly mistakes
  Cashiers must be able to calculate a product

  Scenario Outline: Multiply two numbers
    Given I have entered <x> into the calculator
    And I have entered <y> into the calculator
    When I press multiply
    Then the current value should be <p>

    Examples:
      |  x | y |  p |
      |  2 | 7 | 14 |
      | 10 | 7 | 70 |
