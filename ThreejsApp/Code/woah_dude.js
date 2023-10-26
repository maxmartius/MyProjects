console.log(THREE);
var clock = new THREE.Clock();
var t = 0;
var n=20;
function main(){

    var scene = new THREE.Scene();

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

    initSpheres(scene);

    var dirLight = generateDirectionalLight(0xffffff,10);
    dirLight.position.set(10,10,10);
    scene.add(dirLight);

    update(renderer, scene, camera, controls);

    return scene;

}

function generateBox(w, h, d) {

    var geo = new THREE.BoxGeometry(w , h, d)
    var mat = new THREE.MeshPhongMaterial(
        {color: "rgb(60,70,80)"}
    );
    var mesh = new THREE.Mesh(geo,mat);
    mesh.castShadow = true;
    return mesh;

}

function generateCylinder(height) {

    var geo = new THREE.CylinderGeometry(2,2,height,32);
    var mat = new THREE.MeshPhongMaterial(
        {color: "rgb(255,255,255)"}
    );
    var mesh = new THREE.Mesh(geo,mat);
    mesh.castShadow = true;
    return mesh;

}

function generateSphere() {

    var geo = new THREE.SphereGeometry(2,32,32);
    var mat = new THREE.MeshPhongMaterial(
        {color: "rgb(60,70,80)"}
    );
    var mesh = new THREE.Mesh(geo,mat);
    mesh.castShadow = true;
    return mesh;

}

function generateDirectionalLight(color, intensity){

    var pointLight = new THREE.DirectionalLight(color,intensity);
    pointLight.castShadow = true;
    return pointLight;

}

function update(renderer, scene, camera, controls) {
    renderer.render(scene, camera);

    scene.traverse(function(child){
        //child.position.x += 0.001;
    });

    controls.update();


    handleSpheres(scene);

    addLines(scene);

    requestAnimationFrame(function (){
        update(renderer, scene, camera,controls);
    });
}

function initSpheres(scene) {
    for(var i = 1; i <= n; i++){
        var sphere = generateSphere();
        sphere.position.x = i*5;
        var name = "sphere" + i;
        sphere.name = name;
        scene.add(sphere);
    }

}

function handleSpheres(scene) {
    var step = clock.getDelta();
    for(var i = 1; i <= n; i++ ) {
        var sphere = scene.getObjectByName("sphere"+i);
        t += step * 0.0005;
        sphere.position.x = i*5*Math.cos(t*i);
        sphere.position.z = i*5*Math.sin(t*i);
    }
}

function addLines(scene) {
    var group = new THREE.Group();

    for(var i = 1; i < n; i++ ) {
        scene.remove(scene.children[n+1]);
        var sphere1 = scene.getObjectByName("sphere"+i);
        var sphere2 = scene.getObjectByName("sphere"+(i+1));
        var x = sphere2.position.x - sphere1.position.x;
        var y = sphere2.position.y - sphere1.position.y;
        var z = sphere2.position.z - sphere1.position.z;
        var height = Math.sqrt(x*x+y*y+z*z);
        var rot = Math.acos(x/y);
        var cylinder = generateCylinder(height);
        group.add(cylinder);
        cylinder.position.x = (sphere1.position.x+0.5*x);
        cylinder.position.y = (sphere1.position.y+0.5*y);
        cylinder.position.z = (sphere1.position.z+0.5*z);
        cylinder.rotation.x = Math.PI*0.5;
        //cylinder.rotation.y = rot;

    }
    scene.add(group);
}

var scene = main();
console.log(scene);