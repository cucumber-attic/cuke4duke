% dialyser
-module(cuke).
-export([given/1]).

% Gherkin: Given I have 7 cukes
% given(['I', have, 7, cukes])

% [7]
given(['I', have, X, cukes]) ->
	X;

given(['I', have, X, wives, in, Y]) ->
	X.
		
then(['I', have, X, cukes]) ->
	X;
