#NOTE broken by https://github.com/cucumber/cucumber/commit/37195974a6906a95960c9bbe49b456ea153b2087#diff-1
#Need to use cucumber 1.0.0, not 1.0.1 !
Cucumber::Ast::StepInvocation::BACKTRACE_FILTER_PATTERNS << /cuke4duke|org\/jruby|org\/junit|org\/hamcrest|java\/lang|java\.lang|sun\/reflect|sun\.reflect/
