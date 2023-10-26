#include "Scene.hpp"

#include <assimp/postprocess.h>
#include <assimp/scene.h>

#include <assimp/Importer.hpp>
#include <cassert>
#include <cstddef>
#include <iostream>
#include <vector>
#include <cmath>

Scene::Scene() {}


/**
 * Gibt zurück ob ein gegebener Strahl ein Objekt (Modell oder Kugel) der Szene trifft
 *  (Aufgabenblatt 3)
 */
bool Scene::intersect(const Ray &ray, HitRecord &hitRecord,
                      const float epsilon) {

    bool hit = false;

    for (int i = 0; i < mSpheres.size(); ++i) {
        if (sphereIntersect(ray, mSpheres[i], hitRecord, epsilon)) {
            hit = true;
            hitRecord.sphereId = i;
        }
    }

    for (int i = 0; i < mModels.size(); ++i) {

        GLMatrix trans = mModels[i].getTransformation();

        for (int j = 0; j < mModels[i].mTriangles.size(); ++j) {
            std::array<GLPoint, 3> ver = mModels[i].mTriangles[j].vertex;
            Triangle triangle;
            triangle.vertex[0] = trans * ver[0];
            triangle.vertex[1] = trans * ver[1];
            triangle.vertex[2] = trans * ver[2];
            GLVector normal = crossProduct(triangle.vertex[1] - triangle.vertex[0],
                triangle.vertex[2] - triangle.vertex[0]);
            normal.normalize();
            triangle.normal = normal;

            if (triangleIntersect(ray, triangle, hitRecord, epsilon)) {
                hit = true;
                hitRecord.sphereId = -1;
                hitRecord.modelId = i;
                hitRecord.triangleId = j;
            }
            
        }
    }
    return hit;
}

/** Aufgabenblatt 3: Gibt zurück ob ein gegebener Strahl ein Dreieck  eines Modells der Szene trifft
 *  Diese Methode sollte in Scene::intersect für jedes Objektdreieck aufgerufen werden
 *  Aufgabenblatt 4: Diese Methode befüllt den den HitRecord im Fall eines Treffers mit allen für das shading notwendigen informationen
 */
bool Scene::triangleIntersect(const Ray &ray, const Triangle &triangle,
                              HitRecord &hitRecord, const float epsilon) {

    GLPoint e = ray.origin;
    GLVector dir = ray.direction;
    std::array<GLPoint, 3> vertex = triangle.vertex;
    GLVector n = triangle.normal;
    float ndir = dotProduct(n, dir);

    if (fabs(ndir) < epsilon) return false;

    //float d = dotProduct(n, GLVector(vertex[0](0), vertex[0](1), vertex[0](2)));
    /*
    float d = n(0) * vertex[0](0) + n(1) * vertex[0](1) + n(2) * vertex[0](2);
    float t = (dotProduct(n, GLVector(e(0), e(1), e(2))) + d )/ (ndir);
    */

    float t = dotProduct((vertex[0] - e), n) / dotProduct(dir, n);



    if (t < epsilon) return false;
    if (t > hitRecord.parameter) return false;

    GLPoint P = e + t * dir;

    GLVector c;

    GLVector edge0 = vertex[1] - vertex[0];
    GLVector vp0 = P - vertex[0];
    c = crossProduct(edge0, vp0);
    if (dotProduct(n, c) < 0) return false;

    GLVector edge1 = vertex[2] - vertex[1];
    GLVector vp1 = P - vertex[1];
    c = crossProduct(edge1, vp1);
    if (dotProduct(n, c) < 0) return false;

    GLVector edge2 = vertex[0] - vertex[2];
    GLVector vp2 = P - vertex[2];
    c = crossProduct(edge2, vp2);
    if (dotProduct(n, c) < 0) return false;

    hitRecord.intersectionPoint = P;
    hitRecord.parameter = t;
    hitRecord.normal = n;
    hitRecord.rayDirection = dir;

    return true;
    
}

/** Aufgabenblatt 3: Gibt zurück ob ein gegebener Strahl eine Kugel der Szene trifft
 *  Diese Methode sollte in Scene::intersect für jede Kugel aufgerufen werden
 *  Aufgabenblatt 4: Diese Methode befüllt den den HitRecord im Fall eines Treffers mit allen für das shading notwendigen informationen
*/
bool Scene::sphereIntersect(const Ray &ray, const Sphere &sphere,
                            HitRecord &hitRecord, const float epsilon) {

    GLPoint e = ray.origin;
    GLVector v = ray.direction;
    GLPoint m = sphere.getPosition();
    double r = sphere.getRadius();

    //a = 1-> p,q formel
    //double a = dotProduct(v, v);
    double b = dotProduct(v, (e - m));
    double c = dotProduct((e - m), (e - m)) - r*r;

    //double w = b * b - 4 * a * c;
    double w = b * b - c;
    if (w < 0) {
        return false;
    }
    //eplsilon abfragen und wenn dann kleinstes pos. t finden
    else {
        double t1 = -b + sqrt(w);
        double t2 = -b - sqrt(w);
        double t;
        if (t2 > epsilon) t = t2;
        else if (t1 > epsilon) t = t1;
        else return false;
        if (t > hitRecord.parameter) return false;

        GLPoint s = e + t * v;
        hitRecord.parameter = t;
        hitRecord.intersectionPoint = s;
        hitRecord.rayDirection = v;
        GLVector norm = s - m;
        norm.normalize();
        hitRecord.normal = norm;

        return true;
    }

    //(vv) * t**2 +(2v(e-m))*t + ((e-m)(e-m) - r**2) = 0
    //lös : x1,2 = -b/2 +- w(b/2 **2 - c)
    //lös : x1,2 = (-b+- w(b**2 -4ac))/(2a)

}

/**
** Liest die Modelle (deren Dateinamen in pFiles stehen) ein und speichert sie
*in mModels
**/
void Scene::load(const std::vector<std::string> &pFiles) {
  std::cout << "Laden der PLY Dateien:" << std::endl;
  // Für alle Objekte (Objekt meint hier das selbe wie Model)
  for (int obj_nr = 0; obj_nr < pFiles.size(); obj_nr++) {
    std::cout << "\tModel-Index: " << obj_nr << std::endl;
    // Assimp übernimmt das Einlesen der ply-Dateien
    Assimp::Importer importer;
    const aiScene *assimpScene = importer.ReadFile(
        pFiles[obj_nr], aiProcess_CalcTangentSpace | aiProcess_Triangulate |
                            aiProcess_JoinIdenticalVertices |
                            aiProcess_SortByPType);
    assert(assimpScene);
    auto meshes = assimpScene->mMeshes;
    // Neues Model erstellen
    Model model = Model();

    // Für alle Meshes des Models
    for (int i = 0; i < assimpScene->mNumMeshes; i++) {
      std::cout << "\t\tMesh-Index: " << i << " (" << meshes[i]->mNumFaces
                << " Faces)" << std::endl;
      auto faces = meshes[i]->mFaces;
      auto vertices = meshes[i]->mVertices;

      // Für alle Faces einzelner Meshes
      for (int j = 0; j < meshes[i]->mNumFaces; j++) {
        // Dreieck konstruieren und nötige Werte berechnen
        Triangle tri;
        assert(faces[j].mNumIndices == 3);
        tri.vertex[0] = GLPoint(vertices[faces[j].mIndices[0]].x,
                                vertices[faces[j].mIndices[0]].y,
                                vertices[faces[j].mIndices[0]].z);
        tri.vertex[1] = GLPoint(vertices[faces[j].mIndices[1]].x,
                                vertices[faces[j].mIndices[1]].y,
                                vertices[faces[j].mIndices[1]].z);
        tri.vertex[2] = GLPoint(vertices[faces[j].mIndices[2]].x,
                                vertices[faces[j].mIndices[2]].y,
                                vertices[faces[j].mIndices[2]].z);
        GLVector normal = crossProduct(tri.vertex[1] - tri.vertex[0],
                                       tri.vertex[2] - tri.vertex[0]);
        normal.normalize();
        tri.normal = normal;
        // Jedes Dreieck zum Vector der Dreiecke des aktuellen Models hinzufügen
        model.mTriangles.push_back(tri);
      }
    }
    // Immer das gleiche Material für das Model setzen
    Material material;
    material.color = Color(0.00, 1.00, 0.00);
    model.setMaterial(material);
    // Jedes Model zum Vector der Models der Scene hinzufügen
    mModels.push_back(model);
  }

  std::cout << "Laden der PLY Dateien abgeschlossen." << std::endl;
}

void Scene::setCamera(std::shared_ptr<Camera> cam) { mCamera = cam; }

std::shared_ptr<Camera> Scene::getCamera() const { return mCamera; }

GLPoint Scene::getViewPoint() const {
  if (mCamera)
    return mCamera->getEyePoint();
  else {
    std::cerr << "No Camera set to get view point from." << std::endl;
    return GLPoint(0, 0, 0);
  }
}



void Scene::addPointLight(GLPoint pointLight) {
  mPointLights.push_back(pointLight);
}

void Scene::addModel(Model model) { mModels.push_back(model); }

void Scene::addSphere(Sphere sphere) { mSpheres.push_back(sphere); }

std::vector<Model> &Scene::getModels() { return mModels; }

std::vector<Sphere> &Scene::getSpheres() { return mSpheres; }

std::vector<GLPoint> &Scene::getPointLights() { return mPointLights; }
