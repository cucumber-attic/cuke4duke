# -*- encoding: utf-8 -*-
$LOAD_PATH.unshift File.expand_path("../lib", __FILE__)
require 'cuke4duke/version'

Gem::Specification.new do |s|
  s.name        = 'cuke4duke'
  s.version     = Cuke4Duke::VERSION
  s.authors     = ["Aslak Hellesøy"]
  s.description = 'Write Cucumber Step Definitions in Java, Scala, Groovy, Rhino Javascript, Clojure or Ioke'
  s.summary     = "#{s.name}-#{s.version}"
  s.email       = 'cukes@googlegroups.com'
  s.homepage    = 'http://cukes.info'

  s.add_dependency 'cucumber', '= 1.0.0'
  s.add_development_dependency 'rspec', '>= 2.5.0'
  s.add_development_dependency 'celerity', '>= 0.8.9'
  s.add_development_dependency 'jruby-openssl', '>= 0.7.4'
  s.add_development_dependency 'bundler', '>= 1.0.13'
  s.add_development_dependency 'rake', '>= 0.8.7'

#  s.files            = `git ls-files -- lib`.split("\n") + ["lib/#{Cuke4Duke::JAR_NAME}", 'pom.xml']
  s.files            = `git ls-files -- lib`.split("\n") + ['pom.xml']
  s.executables      = `git ls-files -- bin/*`.split("\n").map{ |f| File.basename(f) }
  s.extra_rdoc_files = ["LICENCE", "README.textile"]
  s.rdoc_options     = ["--charset=UTF-8"]
  s.require_path     = "lib"
end
