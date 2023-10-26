console.log(THREE);

var keyboard = new THREEx.KeyboardState();
var clock = new THREE.Clock();
var moveGroup = new THREE.Group();
var rotaGroup = new THREE.Group();

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


    var box = generateBox(1,1,1);
    box.name = "box-1";
    rotaGroup.add(box);
    moveGroup.add(rotaGroup);
    scene.add(moveGroup);

    var dirLight = generateDirectionalLight(0xffffff,1);
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

function generateDirectionalLight(color, intensity){

    var pointLight = new THREE.DirectionalLight(color,intensity);
    pointLight.castShadow = true;
    return pointLight;

}

function move() {

    var step = clock.getDelta();

    if(keyboard.pressed("W")){
        moveGroup.translateZ(-step);
    }if(keyboard.pressed("A")){
        moveGroup.translateX(-step);
    }if(keyboard.pressed("S")){
        moveGroup.translateZ(step);
    }if(keyboard.pressed("D")){
        moveGroup.translateX(step);
    }if(keyboard.pressed("Q")){
        moveGroup.translateY(step);
    }if(keyboard.pressed("E")){
        moveGroup.translateY(-step);
    }if(keyboard.pressed("F")){
        rotaGroup.rotateX(step);
    }if(keyboard.pressed("H")){
        rotaGroup.rotateX(-step);
    }

}

function update(renderer, scene, camera, controls) {
    renderer.render(scene, camera);

    scene.traverse(function(child){
        //child.position.x += 0.001;
    });

    controls.update();

    move();

    requestAnimationFrame(function (){
        update(renderer, scene, camera,controls);
    });

}

var scene = main();
console.log(scene);