
#include "Model.hpp"

// Konstruktor
Model::Model() {
  /* Aufgabenblatt 2, Aufgabe 3: Setzen Sie die default Werte */
	mTranslation = GLVector(0, 0, 0);
	mRotation = GLVector(0, 0, 0);
	mScale = GLVector(1, 1, 1);
	updateMatrix();
}

// Setter für das Material
void Model::setMaterial(Material material) {
  mMaterial = Material();
  mMaterial.smooth = material.smooth;
  mMaterial.reflection = material.reflection;
  mMaterial.refraction = material.refraction;
  mMaterial.transparency = material.transparency;
  mMaterial.color = Color(material.color.r, material.color.g, material.color.b);
}

/* Aufgabenblatt 2, Aufgabe 3: Implementieren Sie die vier Methoden für die Transformationen hier */

void Model::setRotation(GLVector rotation) {
	mRotation = mRotation + rotation;
	updateMatrix();
}
void Model::setTranslation(GLVector translation) {
	mTranslation = mTranslation + translation;
	updateMatrix();
}
void Model::setScale(GLVector scale) {
	mScale = GLVector(mScale(0) * scale(0), mScale(1) * scale(1), mScale(2) * scale(2));
	updateMatrix();
}
void Model::updateMatrix() {

	mMatrix = GLMatrix();


	GLMatrix trans = GLMatrix();
	trans.setColumn(3, mTranslation);
	mMatrix = mMatrix * trans;


	GLMatrix scale = GLMatrix();
	scale.setValue(0, 0, mScale(0));
	scale.setValue(1, 1, mScale(1));
	scale.setValue(2, 2, mScale(2));
	mMatrix = mMatrix * scale;


	GLMatrix yRot = GLMatrix();
	yRot.setValue(0, 0, cos(mRotation(1)));
	yRot.setValue(0, 2, sin(mRotation(1)));
	yRot.setValue(2, 0, -sin(mRotation(1)));
	yRot.setValue(2, 2, cos(mRotation(1)));
	mMatrix = mMatrix * yRot;


	GLMatrix xRot = GLMatrix();
	xRot.setValue(1, 1, cos(mRotation(0)));
	xRot.setValue(1, 2, -sin(mRotation(0)));
	xRot.setValue(2, 1, sin(mRotation(0)));
	xRot.setValue(2, 2, cos(mRotation(0)));
	mMatrix = mMatrix * xRot;


	GLMatrix zRot = GLMatrix();
	zRot.setValue(0, 0, cos(mRotation(2)));
	zRot.setValue(0, 1, -sin(mRotation(2)));
	zRot.setValue(1, 0, sin(mRotation(2)));
	zRot.setValue(1, 1, cos(mRotation(2)));
	mMatrix = mMatrix * zRot;


}


GLMatrix Model::getTransformation() const { return mMatrix; }

Material Model::getMaterial() const { return mMaterial; }
