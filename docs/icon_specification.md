
Icon specification
===================

File format
-------------

Icons are stored as **.png** images


Sizes
--------

The pixel size of each icon size is:
+ Small 20x20
+ Medium 80x80
+ Large 200X200

Each icon should be in all sizes and be the correct size as specified above. Icons should never be upscaled to the large size (200px sq.) and should natively be that size.


Directory structure
-------------------

All icons should be stored in:
        resources/icons/[size]
were [size] is the size of the icon as specified above.

Files should always be named in all lower case and have a .png file extension.


Using icons in the code
----------------------

Icons should always be referenced using the method *ImageIcon getIcon(String iconName)* from the class *R*.


