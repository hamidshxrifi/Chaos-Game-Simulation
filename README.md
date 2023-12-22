# Chaos-Game-Simulation
## How does chaos game work?
Chaos game is played by first constructing a traingle, and then choosing a random point on the plane. A vertex of the triangle is randomly chosen and a new point is drawn at the midpoint (**r = 0.5**) of the initial point and the chosen vertex. The process is then repeated iteratively until a fractal is formed. This can be further applied to different shapes and **r values** to create complex fractals from simple rules.

The r value determines the position of the next point to be drawn between the previous point and the chosen vertex. Some valid inputs for r include midpoint (r = 0.5), one-third of the way (r = 0.33), and three-quarters of the way (r = 0.75). To produce a fractal with optimal packing of points, set the r value to n/(n+3), where n is the amount of vertices of the shape.

Further restrictions can also be applied to the rules in order to produce fractals with different behaviour. For example, not allowing the algorithm to choose the same vertex twice in a row.



## Program Details
* Left click --> place shape vertex
* Right click --> place initial point and start simulation
* While simulating, right click --> finish simulation
<br>

![image](https://github.com/hamidshxrifi/Chaos-Game-Simulation/assets/97482941/38ad3251-1e57-4ec5-8f46-89da7b6cba23)




