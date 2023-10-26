#include "SolidRenderer.hpp"

//#include <tbb/tbb.h>  // Include, nur wenn TBB genutzt werden soll

#define EPSILON \
  (1e-12)  // Epsilon um ungenauigkeiten und Rundungsfehler zu kompensieren

/**
 ** Erstellt mittels Raycast das Rendering der mScene in das mImage
 ** Precondition: Sowohl mImage, mScene, mCamera müssen gesetzt sein.
 **/
void SolidRenderer::renderRaycast() {
  // This function is part of the base

  std::cout << "Rendern mittels Raycast gestartet." << std::endl;
  // Berechnung der einzelnen Rows in eigener Methode um die
  // Parallelisierbarkeit zu verbessern

  // Ohne parallelisierung:

  //for(size_t i = 0; i < mImage->getHeight(); ++i ){
    //      computeImageRow( i );
  //}

  //  Parallelisierung mit OpenMP:

  #pragma omp parallel for
      for(int i = 0; i < mImage->getHeight(); ++i )
      {
          computeImageRow( i );
      }

  // Parallelisierung mit TBB:

  //tbb::parallel_for(size_t(0), mImage->getHeight(),
  //                /* lambda funktion*/ [&](size_t i) { computeImageRow(i); });
}

/**
 * Aufgabenblatt 3: Hier wird das Raycasting implementiert. Siehe Aufgabenstellung!
 * Precondition: Sowohl mImage, mScene und mCamera  müssen gesetzt sein.
 */
 void SolidRenderer::computeImageRow(size_t rowNumber) {
     for (int i = 0; i < mImage->getWidth(); ++i) {
         Ray ray = mCamera->getRay(i, rowNumber);
         HitRecord hr = HitRecord();
         hr.color = Color(0.0, 0.0, 0.0);
         hr.parameter = 10000000.0;
         hr.sphereId = -1;
         hr.modelId = -1;
         hr.triangleId = -1;
         hr.recursions = 0;

         if (mScene->intersect(ray, hr, EPSILON)) {
             shade(hr);
         }
         mImage->setValue(i, rowNumber, hr.color);
     }

 }

/**
 *  Aufgabenblatt 4: Hier wird das raytracing implementiert. Siehe Aufgabenstellung!
 */
void SolidRenderer::shade(HitRecord &hr) {
    Material mat;
    int no = 1;
    if (hr.sphereId == -1) {
        mat = mScene->getModels()[hr.modelId].getMaterial();
        if (hr.modelId == 1) {
            no = -1;
        }
    }
    else {
        mat = mScene->getSpheres()[hr.sphereId].getMaterial();

    }

    double Ka = 0.4;
    double Kd = 0.4;
    double Ks = 0.2;

    GLVector L = mScene->getPointLights()[0] - hr.intersectionPoint;
    L.normalize();
    GLVector N = no * hr.normal;
    GLVector R = 2 * (dotProduct(N, L)) * N + -1 * L;
    R.normalize();
    GLVector V = -1 * hr.rayDirection;
    double n = 10;

    if(mat.reflection > 0.0 && hr.recursions < 5){
        // create new reflection ray
        Ray reflectionRay = Ray();
        reflectionRay.origin = hr.intersectionPoint + EPSILON * N; // + 8 * N; // + -0.001 * R;
        GLVector reflectionDir = 2 * (dotProduct(N, V)) * N + -1 * V;
        reflectionDir.normalize();
        reflectionRay.direction = reflectionDir;

        // reset hr
        hr.color = Color(0.0, 0.0, 0.0);
        hr.parameter = 10000000.0;
        hr.sphereId = -1;
        hr.modelId = -1;
        hr.triangleId = -1;
        hr.recursions += 1;
        // std::cout << L << " " << N << " "<< R << " " << hr.recursions << std::endl;

        if(mScene->intersect(reflectionRay, hr, EPSILON)){
            shade(hr);
        }

        // theoretically change color of hr depending on perfection of reflection hr.color *= mat.reflection;
    }else {
        hr.color = mat.color;

        double I = Ka  + Kd  * std::max((dotProduct(L, N)), 0.0) + Ks  * std::pow(std::max(dotProduct(R, V), 0.0), n);
        hr.color *= I;

        Ray shadowray = Ray();
        shadowray.origin = hr.intersectionPoint + 0.001 * N;
        shadowray.direction = L;
        HitRecord shadowhr = HitRecord();
        shadowhr.parameter = (mScene->getPointLights()[0] - hr.intersectionPoint).norm();
        if (mScene->intersect(shadowray, shadowhr, EPSILON)) {
            hr.color *= 0.4;
        }
    }
}
