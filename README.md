# fruitTreeAnalyser
A program that outlines fruit on a tree with boxes.

The objective of this CA was to create a JavaFX application that can be supplied with an image
of a fruit tree bearing fruit. When given the image, the application should locate individual (and clusters of) fruit on the 
tree and estimate how many of them are in the image overall. 

The key aspect of this CA was to use a union-find algorithm to locate the individual
fruits/clusters.

I created a user-friendly interface that let's the user upload an image and then using a slider change to change the hue 
value of the original image once it gets converted to black and white.

The user would then click on the fruit to let the program know what it's looking for.

Each pixel in the black-and-white image was initially considered a disjoint set, with this
information potentially represented in an array. Union-find was then be applied to union
adjacent white pixels (up, down, left, and right) to identify fruits/clusters. Black pixels were disregarded.

Once the union-find is complete, the individual disjoint sets are processed to identify the
set pixel boundaries. The size of the boundary suggests whether a disjoint set
is likely to be a valid fruit cluster or not.

An estimate of the number fruits/clusters in the image and the pixel unit/disjoint set sizes are displayed once hovered over.
