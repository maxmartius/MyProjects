#include <assimp/postprocess.h>
#include <assimp/scene.h>
//#include <unistd.h>

#include <algorithm>
#include <assimp/Importer.hpp>
#include <cmath>
#include <iostream>
#include <limits>
#include <string>
#include <vector>

#include "Scene.hpp"
#include "SolidRenderer.hpp"
#include "WireframeRenderer.hpp"
#include "math.hpp"

int main(int argc, char **argv) {
  // Dimensionen des Ergebnisbildes im Konstruktor setzen
  std::shared_ptr<Image> img = std::make_shared<Image>(401, 301);

  // Verwendete Modelle festlegen
  std::vector<std::string> path_vector;
  path_vector.push_back(std::string("../data/bunny/bunny_scaled.ply"));
  path_vector.push_back(std::string("../data/basicObjects/cube_scaled.ply"));
  // Erzeuge die Szene mit dem default Konstruktor und lade die Modelle
  auto scene = std::make_shared<Scene>();
  scene->load(path_vector);


  /* Aufgabenblatt 1: Instanziieren Sie einen WireframeRenderer */

  std::shared_ptr<WireframeRenderer> WfR = std::make_shared<WireframeRenderer>(scene, img);

  /* Aufgabenblatt 1, Aufgabe 2: Testen Sie Ihre drawBresenhamLine-Methode hier */
  
  //WfR->drawBresenhamLine(GLPoint(200,150,0), GLPoint(100,170,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(200,150,0), GLPoint(180,250,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(200,150,0), GLPoint(220,250,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(200,150,0), GLPoint(300,170,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(200,150,0), GLPoint(300,130,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(200,150,0), GLPoint(220,50,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(200,150,0), GLPoint(180,50,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(200,150,0), GLPoint(100,130,0), Color(0.0, 0.7, 0.0));

  //Dreieck
  //WfR->drawBresenhamLine(GLPoint(100,130,0), GLPoint(180,50,0), Color(0.0, 0.7, 0.0));

  //Loch
  //WfR->drawBresenhamLine(GLPoint(150,120,0), GLPoint(150,100,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(150,100,0), GLPoint(180,100,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(180,100,0), GLPoint(180,120,0), Color(0.0, 0.7, 0.0));
  //WfR->drawBresenhamLine(GLPoint(180,120,0), GLPoint(150,120,0), Color(0.0, 0.7, 0.0));



  /* Aufgabenblatt 1, Aufgabe 3: Testen Sie Ihre seedFillArea-Methode hier */  

  //WfR->seedFillArea(GLPoint(140, 99, 0), Color(0.0, 0.7, 0.0), Color(0.7, 0.0, 0.0));

  /* Aufgabenblatt 2, Aufgabe 3: Setzen Sie die Transformationen der Modelle */
  /*
  std::vector<Model> &models = scene->getModels();
  Model& bunny = models[0];
  bunny.setTranslation(GLVector(250, 100, 0));
  bunny.setScale(GLVector(0.8, 0.8, 0.8));
  bunny.setRotation(GLVector(0.0, 5.0 * 3.1415 / 180.0, 0.0));
  Model& cube = models[1];
  cube.setTranslation(GLVector(100, 100, 0));
  cube.setScale(GLVector(0.9, 0.9, 0.9));
  cube.setRotation(GLVector(20.0 * 3.1415 / 180.0, 45.0 * 3.1415 / 180.0, 0));
  */
  /* Aufgabenblatt 2, Aufgabe 1: Rufen Sie Ihre renderScene-Methode hier auf */
  //WfR->renderScene(Color(0.0, 0.7, 0.0));

  


  /* Setup der Camera - Erst ab Aufgabenblatt 3 relevant. */
  // Diese Einstellungen beziehen sich auf den world space
  // Beachten Sie, dass Sie in diesem Praktikum keine explizite Umwandlung in
  // den ViewSpace benötigen, da die Strahen für Raycasting und Raytracing im
  // World space definiert sind. Modelle müssen also lediglich in den World
  // space überführt werden
  
  /* Aufgabenblatt 3:  kommentieren Sie die Zeilen wieder ein, die eine Kamera erzeugen und zur Scene hinzufügen*/
  
  auto cam = std::make_shared<Camera>();
  GLPoint eye = GLPoint(0.0, 0.0, 300.0);
  cam->setEyePoint(eye);
  cam->setUp(GLVector(0.0, 1.0, 0.0));
  GLVector viewDirection = GLVector(0.0, 0, -1.0);
  viewDirection.normalize();
  cam->setViewDirection(viewDirection);
  cam->setSize(img->getWidth(), img->getHeight());
  scene->setCamera(cam);


  /* Aufgabenblatt 3: Erzeugen Sie mindestens eine Kugel und fügen Sie diese zur Szene hinzu*/ 
  
  auto sphere1 = Sphere();
  auto sphere2 = Sphere();
  scene->addSphere(sphere1);
  scene->addSphere(sphere2);

  /* Aufgabenblatt 4: Setzen Sie materialeigenschaften für die Kugelen und die Modelle. Die Materialeigenschaften für eine Darstellung entsprechend der Beispiellösung ist in der Aufgabenstellung gegeben. */

  auto mat1 = Material();
  mat1.color = Color(0, 1, 0);
  auto mat2 = Material();
  mat2.color = Color(0.8, 0.8, 0.8);
  auto mat3 = Material();
  mat3.color = Color(0, 0, 1);
  mat3.reflection = 1.0;

  /* Aufgabenblatt 3: (Wenn nötig) Transformationen der Modelle im World space, sodass sie von der Kamera gesehen werden könnnen. Die nötigen Transformationen für eine Darstellung entsprechend der Beispiellösung ist in der Aufgabenstellung gegeben. */

  std::vector<Model>& models = scene->getModels();
  Model& bunny = models[0];
  bunny.setTranslation(GLVector(0, -10, -30));
  bunny.setScale(GLVector(1.5, 1.5, 1.5));
  bunny.setRotation(GLVector(0.0, 170.0 * 3.1415 / 180.0, 0.0));
  Model& cube = models[1];
  cube.setTranslation(GLVector(0, 10, 0));
  cube.setScale(GLVector(10, 10, 10));

  std::vector<Sphere>& spheres = scene->getSpheres();
  Sphere& sphere1n = spheres[0];
  sphere1n.setPosition(GLPoint(-200, -200, -150));
  sphere1n.setRadius(150);
  Sphere& sphere2n = spheres[1];
  sphere2n.setPosition(GLPoint(200, -200, -150));
  sphere2n.setRadius(150);

  /* Stelle materialeigenschaften zur verfügung (Relevant für Aufgabenblatt 4)*/

  bunny.setMaterial(mat1);
  cube.setMaterial(mat2);
  sphere1n.setMaterial(mat3);
  sphere2n.setMaterial(mat3);

  /* Aufgabenblatt 4  Fügen Sie ein Licht zur Szene hinzu */
  scene->addPointLight(GLPoint(-100, 100, 200));
  
    
  /* Aufgabenblatt 3: erzeugen Sie einen SolidRenderer (vorzugsweise mir einem shared_ptr) und rufen sie die Funktion renderRaycast auf */

  std::shared_ptr<SolidRenderer> SR = std::make_shared<SolidRenderer>(scene, img, cam);
  SR->renderRaycast();


  // Schreiben des Bildes in Datei
  if (argc > 1) {
    img->writeAsPPM(argv[1]);
    std::cout << "Bild mit Dimensionen " << img->getWidth() << "x"
              << img->getHeight() << " in Datei " << argv[1] << " geschrieben."
              << std::endl;
  } else {
    std::cerr
        << "Fehler: Kein Dateiname angegeben. Es wurde kein Output generiert."
        << std::endl;
  }

  return 0;
}
