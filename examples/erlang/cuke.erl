% dialyser
-module(cuke).
-export([given/1]).

% Gherkin: Given I have 7 cukes
% given(['I', have, 7, cukes])
% [7]

% Gherkin: Given I have 3 wives in Sweden

given(['I', have, X, cukes]) ->
	X;

given(['I', have, X, wives, in, Y]) ->
	X.
		
given(['I', have, X, cukes]) when X <= 6 ->
	X;
	
function_name(X) when X > 0 -> 
   ;
