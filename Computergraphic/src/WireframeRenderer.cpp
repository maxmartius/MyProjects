
#include "WireframeRenderer.hpp"
#include <stack>



/**
** Zeichnet alle Dreiecke der Scene als Wireframe-Rendering unter Verwendung des
* Bresenham-Algorithmus
** Precondition: Sowohl mImage als auch mScene müssen gesetzt sein.
** (Aufgabenblatt 2 - Aufgabe 1)
**/
void WireframeRenderer::renderScene(Color color) {

	std::vector<Model> models = WireframeRenderer::mScene->getModels();
	for each(Model model in models) {

		GLMatrix trans = model.getTransformation();

		std::vector<Triangle> tries = model.mTriangles;
		for each (Triangle tri in tries){
			std::array<GLPoint, 3> vertex = tri.vertex;
			vertex[0] = trans * vertex[0];
			vertex[1] = trans * vertex[1];
			vertex[2] = trans * vertex[2];
			WireframeRenderer::drawBresenhamLine(vertex[0], vertex[1], color);
			WireframeRenderer::drawBresenhamLine(vertex[1], vertex[2], color);
			WireframeRenderer::drawBresenhamLine(vertex[2], vertex[0], color);

		}

	}

}

/**
** Zeichnet unter Verwendung des Bresenham Algorithmus eine Linie zwischen p1
* und p2 (nutzt x & y Komponente - z Komponente wird ignoriert)
** Precondition: Das mImage muss gesetzt sein.
** (Aufgabenblatt 1 - Aufgabe 2)
**/
void WireframeRenderer::drawBresenhamLine(GLPoint p1, GLPoint p2, Color color) {
	int dx = p2(0) - p1(0);
	int dy = p2(1) - p1(1);

	if (dy < 0) { dy *= -1; }
	if (dx < 0) { dx *= -1; }
	//rechte Oktanten
	if (p1(0) <= p2(0)) {
		//obere Oktanten
		if (p1(1) <= p2(1)) {
			if (dy <= dx) {
				drawBresenhamLineOkt1(p1, p2, color);
			}
			else {
				drawBresenhamLineOkt2(p1, p2, color);
			}

		}
		//untere Oktanten
		else {
			if (dy <= dx) {
				drawBresenhamLineOkt8(p1, p2, color);
			}
			else {
				drawBresenhamLineOkt7(p1, p2, color);
			}

		}
	
	}
	//linken Oktanten
	else {
		//obere Oktanten
		if (p1(1) <= p2(1)) {
			if (dy <= dx) {
				drawBresenhamLineOkt4(p1, p2, color);
			}
			else {
				drawBresenhamLineOkt3(p1, p2, color);
			}

		}
		//untere Oktanten
		else {
			if (dy <= dx) {
				drawBresenhamLineOkt5(p1, p2, color);
			}
			else {
				drawBresenhamLineOkt6(p1, p2, color);
			}

		}

	}

}

void WireframeRenderer::drawBresenhamLineOkt1(GLPoint p1, GLPoint p2, Color color) {
	int x = p1(0);
	int y = p1(1);
	int dx = p2(0) - p1(0);
	int dy = p2(1) - p1(1);
	int e = 2 * dy - dx;
	for (int i = 1; i <= dx; i++) {
		WireframeRenderer::mImage->setValue(x, y, color);
		if (e >= 0) {
			++y;
			e -= 2 * dx;
		}
		++x;
		e += 2 * dy;
	
	}
	WireframeRenderer::mImage->setValue(x, y, color);
}
void WireframeRenderer::drawBresenhamLineOkt2(GLPoint p1, GLPoint p2, Color color) {
	int x = p1(0);
	int y = p1(1);
	int dx = p2(0) - p1(0);
	int dy = p2(1) - p1(1);
	int e = 2 * dx - dy;
	for (int i = 1; i <= dy; i++) {
		WireframeRenderer::mImage->setValue(x, y, color);
		if (e >= 0) {
			++x;
			e -= 2 * dy;
		}
		++y;
		e += 2 * dx;

	}
	WireframeRenderer::mImage->setValue(x, y, color);
}
void WireframeRenderer::drawBresenhamLineOkt3(GLPoint p1, GLPoint p2, Color color) {
	int x = p1(0);
	int y = p1(1);
	int dx = -(p2(0) - p1(0));
	int dy = p2(1) - p1(1);
	int e = 2 * dx - dy;
	for (int i = 1; i <= dy; i++) {
		WireframeRenderer::mImage->setValue(x, y, color);
		if (e >= 0) {
			--x;
			e -= 2 * dy;
		}
		++y;
		e += 2 * dx;

	}
	WireframeRenderer::mImage->setValue(x, y, color);
}
void WireframeRenderer::drawBresenhamLineOkt4(GLPoint p1, GLPoint p2, Color color) {
	int x = p1(0);
	int y = p1(1);
	int dx = -(p2(0) - p1(0));
	int dy = p2(1) - p1(1);
	int e = 2 * dy - dx;
	for (int i = 1; i <= dx; i++) {
		WireframeRenderer::mImage->setValue(x, y, color);
		if (e >= 0) {
			++y;
			e -= 2 * dx;
		}
		--x;
		e += 2 * dy;

	}
	WireframeRenderer::mImage->setValue(x, y, color);
}
void WireframeRenderer::drawBresenhamLineOkt5(GLPoint p1, GLPoint p2, Color color) {
	int x = p1(0);
	int y = p1(1);
	int dx = -(p2(0) - p1(0));
	int dy = -(p2(1) - p1(1));
	int e = 2 * dy - dx;
	for (int i = 1; i <= dx; i++) {
		WireframeRenderer::mImage->setValue(x, y, color);
		if (e >= 0) {
			--y;
			e -= 2 * dx;
		}
		--x;
		e += 2 * dy;

	}
	WireframeRenderer::mImage->setValue(x, y, color);
}
void WireframeRenderer::drawBresenhamLineOkt6(GLPoint p1, GLPoint p2, Color color) {
	int x = p1(0);
	int y = p1(1);
	int dx = -(p2(0) - p1(0));
	int dy = -(p2(1) - p1(1));
	int e = 2 * dx - dy;
	for (int i = 1; i <= dy; i++) {
		WireframeRenderer::mImage->setValue(x, y, color);
		if (e >= 0) {
			--x;
			e -= 2 * dy;
		}
		--y;
		e += 2 * dx;

	}
	WireframeRenderer::mImage->setValue(x, y, color);
}
void WireframeRenderer::drawBresenhamLineOkt7(GLPoint p1, GLPoint p2, Color color) {
	int x = p1(0);
	int y = p1(1);
	int dx = p2(0) - p1(0);
	int dy = -(p2(1) - p1(1));
	int e = 2 * dx - dy;
	for (int i = 1; i <= dy; i++) {
		WireframeRenderer::mImage->setValue(x, y, color);
		if (e >= 0) {
			++x;
			e -= 2 * dy;
		}
		--y;
		e += 2 * dx;

	}
	WireframeRenderer::mImage->setValue(x, y, color);
}
void WireframeRenderer::drawBresenhamLineOkt8(GLPoint p1, GLPoint p2, Color color) {
	int x = p1(0);
	int y = p1(1);
	int dx = p2(0) - p1(0);
	int dy = -(p2(1) - p1(1));
	int e = 2 * dy - dx;
	for (int i = 1; i <= dx; i++) {
		WireframeRenderer::mImage->setValue(x, y, color);
		if (e >= 0) {
			--y;
			e -= 2 * dx;
		}
		++x;
		e += 2 * dy;

	}
	WireframeRenderer::mImage->setValue(x, y, color);
}



/**
** Füllt einen vorgegebenen Bereich (abgegrenzt durch Randfarbe/borderColor) mit einer vorgegebenen Farbe (fillColor).
** Precondition: Das mImage muss gesetzt sein.
** (Aufgabenblatt 1 - Aufgabe 3) 
**/
void WireframeRenderer::seedFillArea(GLPoint seed, Color borderColor, Color fillColor) {

	std::stack<GLPoint> stack;
	
	stack.push(seed);
	while (!stack.empty()) {

		GLPoint p1 = stack.top();
		stack.pop();

		if (!(WireframeRenderer::mImage->getValue(p1(0),p1(1)) == borderColor || WireframeRenderer::mImage->getValue(p1(0), p1(1)) == fillColor)) {
			
			WireframeRenderer::mImage->setValue(p1(0), p1(1), fillColor);
			if (p1(0) + 1 < WireframeRenderer::mImage->getWidth()) { stack.push(GLPoint(p1(0) + 1, p1(1), 0)); }
			if (p1(0) - 1 >= 0) { stack.push(GLPoint(p1(0) - 1, p1(1), 0)); }
			if (p1(1) + 1 < WireframeRenderer::mImage->getHeight()) { stack.push(GLPoint(p1(0), p1(1) + 1, 0)); }
			if (p1(1) - 1 >= 0) { stack.push(GLPoint(p1(0), p1(1) - 1, 0)); }

		}
	}

}
