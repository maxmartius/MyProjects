
var keyboard = new THREEx.KeyboardState();
var clock = new THREE.Clock();


function init(){
    var scene = new THREE.Scene();
    var leftLight = generateSpotLight("rgb(255,200,200)",0.5);
    var rightLight = generateSpotLight("rgb(255,200,200)",1.2);
    leftLight.position.set(6,8,10);
    rightLight.position.set(30,20,-10);

    var filenames = ["px","nx","py","ny","pz","nz"];
    var cube = new THREE.CubeTextureLoader().load(filenames.map(function(filename){
        return "/img/cubemap/" + filename + ".jpg";
        }
    ));
    scene.background = cube;
    scene.add(leftLight);
    scene.add(rightLight);

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

    var objLoader = new THREE.OBJLoader();
    var textureLoader = new THREE.TextureLoader();

    objLoader.load("/img/wolf/Wolf_obj.obj",function (object) {
        var body = textureLoader.load("/img/wolf/textures/Wolf_Body.jpg");
        var fur = textureLoader.load("/img/wolf/textures/Wolf_Fur.jpg");

        var bodyMat = new THREE.MeshStandardMaterial({color:"rgb(255,255,255)"});
        var furMat = new THREE.MeshStandardMaterial({color:"rgb(255,255,255)"});

        object.traverse(function (child) {
            if(child.name == "Wolf_obj_body_Cube.001"){
                child.material = bodyMat;
                bodyMat.roughness =  0.8;
                bodyMat.map = body;
                bodyMat.metalness = 0;
            }
            if(child.name == "Wolf_obj_fur_Cube.002"){
                child.material = furMat;
                furMat.roughness =  0.8;
                furMat.map = fur;
                furMat.metalness = 0;
            }

        })

        object.scale.x = 4;
        object.scale.y = 4;
        object.scale.z = 4;
        object.name = "wolf";

        scene.add(object);
    });

    var particleSystem = new THREE.Geometry();
    var particleMat = new THREE.PointsMaterial({
        color:"rgb(255,255,255)",
        size: 0.5,
        map : new THREE.TextureLoader().load("/img/particle.jpg"),
        transparent: true,
        blending: THREE.AdditiveBlending,
        depthWrite: false
    });

    var particleAmount = 10000;
    for(var i = 0; i < particleAmount; i++){
        var particle = new THREE.Vector3(Math.random()*200-100, Math.random()*200-100, Math.random()*200-100);
        particleSystem.vertices.push(particle);
    }
    var particles = new THREE.Points(particleSystem,particleMat);
    scene.add(particles);

    var renderer = new THREE.WebGLRenderer();
    renderer.shadowMap.enabled = true;
    renderer.setSize(window.innerWidth,window.innerHeight);
    renderer.setClearColor("rgb(60,60,60)");
    document.getElementById("webgl").append(renderer.domElement);

    var controls = new THREE.OrbitControls(camera, renderer.domElement);

    update(renderer, scene, camera, controls);

    return scene;

}

function generateSpotLight(color, intensity){
    var light = new THREE.SpotLight(color,intensity);
    light.castShadow = true;
    light .penumbra = 0.5;
    light.shadow.mapSize.width = 2048;
    light.shadow.mapSize.height = 2048;
    light.shadow.bias = 0.001;
    return light;

}

function update(renderer, scene, camera, controls) {
    renderer.render(scene, camera);
    controls.update();

    var wolf = scene.getObjectByName("wolf");
    move(wolf);

    requestAnimationFrame(function () {
        update(renderer,scene,camera,controls);
    })

}

function move(object, camera) {

    var step = 10 * clock.getDelta();
    if(keyboard.pressed("A")){
        object.translateX(step);
    }if(keyboard.pressed("D")){
        object.translateX(-step);
    }if(keyboard.pressed("W")){
        object.rotation.y = 0;
        object.translateZ(step);
    }if(keyboard.pressed("S")) {
        object.rotation.y = Math.PI;
        object.translateZ(step);
    }
}

var scene = init()