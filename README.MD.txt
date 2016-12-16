The program will be run using by running App with the name of all classes to
as generate a UML for as the command argument. This is where the main class is
located. The recursive parsing flag is false unless a -r flag is specified,
and the access level flag is set to private unless a -a=<level> flag is
specified (private, protected or public).

M1 Contributions

Chuck:
  Worked on parsing classes to match UML format and get 
  other needed interfaces and superclasses and making 
  the access level flag work.

Eric:
  Worked on creating graphViz code form ClassContent and the 
  recursive addition of new classes (recursive flag).

Logan:
  Updated and cleared up UML design and worked on 
  creating graphviz code form CLassContent.  Set up
  main.  Began implementing code to execute graphViz
  to automattically generate a png.  