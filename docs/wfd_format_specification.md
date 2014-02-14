
Wire frame Designer format specification
------------------------------


The wfd file format contains a comma separated list of elements. Each wireframe object should take up a single line of
this file with the first element being the name of the wireframe type (which should be the same as the return value of
*public String getTypeOfWireframe();* contained within *Wireframe*. The following values in the comma separated list
can represent whatever is desired depending on the type of Wireframe and the values which are required to reproduce a
Wireframe of that type. The number of elements should however always be non-zero.


Saving and loading from the save string
---------------------------------------

The creation of the save string should be handled by the method *public String getSaveString();* contained within the
Wireframe object. It is important to note that this should include the first element in the CSV list which is the name
of the type of Wireframe.

Every object which implements Wireframe should include a static method
        public static Wireframe makeWireframe(String[] args);
This method should create a new wireframe object of that type based on the save string (which has already been split
into an array with one value per element in the array.

On top of the static method there should be an entry in the if statement at **FileUtil:29**. This is where the static
method will be called to create the Wireframe object. Without an entry to this statement it will not be able to load
that type of Wireframe from a file.