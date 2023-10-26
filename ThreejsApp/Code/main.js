console.log(THREE);

var keyboard = new THREEx.KeyboardState();
var clock = new THREE.Clock();

function main() {
    var scene = new THREE.Scene();

    var gui = new dat.GUI();

    var box = generateBox(1,1,1);
    box.translateY(box.geometry.parameters.height/2);
    box.name = "box-1";

    var floor = generateFloor(10,20);
    floor.rotation.x = Math.PI*0.5;
    floor.name = "floor";

    var light = generatePointLight(0xffffff, 1);
    light.position.y = 10;
    light.position.x = -5;

    scene.add(generateMoon());

    gui.add(light, "intensity",0,20);

    floor.add(box);
    scene.add(floor);
    scene.add(light);

    var camera = new THREE.PerspectiveCamera(
        45,
        window.innerWidth/ window.innerHeight,
        1,
        1000,
    );
    camera.position.x = 0;
    camera.position.y = 10;
    camera.position.z = 10;
    camera.lookAt(new THREE.Vector3(0,0,-5));

    var renderer = new THREE.WebGLRenderer();
    renderer.shadowMap.enabled = true;
    renderer.setSize(window.innerWidth,window.innerHeight);
    renderer.setClearColor("rgb(60,60,60)");
    document.getElementById("webgl").append(renderer.domElement);

    var controls = new THREE.OrbitControls(camera, renderer.domElement);

    update(renderer, scene, camera, controls);

    return scene;
}

function  generateFloor(w, d){

    var geo = new THREE.PlaneGeometry(w , d)
    var mat = new THREE.MeshPhongMaterial(
        {color: "rgb(60,60,60)",
        side: THREE.DoubleSide}
    );
    var mesh = new THREE.Mesh(geo,mat);
    mesh.receiveShadow = true;
    return mesh;

};

function generateMoon(){

    var sphere = new THREE.SphereGeometry(3,42,42);
    var moonTexture = THREE.ImageUtils.loadTexture("/img/moon.jpg");
    var moonMat = new THREE.MeshLambertMaterial({map: moonTexture});
    var moon = new THREE.Mesh(sphere, moonMat);
    moon.position.x = -25;
    moon.position.y = 25;
    moon.position.z = 25;
    return moon;


}

function generateBox(w, h, d) {

    var geo = new THREE.BoxGeometry(w , h, d)
    var mat = new THREE.MeshPhongMaterial(
        {color: "rgb(100,100,100)"}
    );
    var mesh = new THREE.Mesh(geo,mat);
    mesh.castShadow = true;
    return mesh;

}

function generatePointLight(color, intensity){

    var pointLight = new THREE.PointLight(color, intensity);
    pointLight.castShadow = true;
    return pointLight;

}

function update(renderer, scene, camera, controls) {
   renderer.render(scene, camera);

    var floor = scene.getObjectByName("floor");

    floor.rotation.y += 0.002;

    scene.traverse(function(child){
        //child.position.x += 0.001;
    });

    controls.update();

    var step = 10 * clock.getDelta();

    var box = scene.getObjectByName("box-1");
    if(keyboard.pressed("A")){
        box.translateX(-step);
    }if(keyboard.pressed("D")){
        box.translateX(step);
    }if(keyboard.pressed("W")){
        box.translateY(-step);
    }if(keyboard.pressed("S")) {
        box.translateY(step);
    }

    requestAnimationFrame(function (){
        update(renderer, scene, camera,controls);
    });

}


var scene = main();

console.log(scene);