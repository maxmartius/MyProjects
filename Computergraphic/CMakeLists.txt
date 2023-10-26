cmake_minimum_required(VERSION 3.0)
project(CGPraktikum)

###############################################################################
## file globbing ##############################################################
###############################################################################

# these instructions search the directory tree when cmake is
# invoked and put all files that match the pattern in the variables 
# `sources` and `data`
file(GLOB sources  ./src/*.cpp ./src/*.hpp)
# you can use set(sources src/main.cpp) etc if you don't want to
# use globing to find files automatically

# set the default build type if none was set
if( NOT CMAKE_BUILD_TYPE AND NOT CMAKE_CONFIGURATION_TYPES )
    message( STATUS "Setting build type to 'Debug' as none was specified." )
    set( CMAKE_BUILD_TYPE Debug CACHE STRING "Choose the type of build." FORCE )
    # set the possible values of build type for cmake-gui
    set_property( CACHE CMAKE_BUILD_TYPE PROPERTY STRINGS "Debug" "Release" "MinSizeRel" "RelWithDebInfo" )
endif()

###############################################################################
## target definitions #########################################################
###############################################################################



# add the data to the target, so it becomes visible in some IDE
add_executable( CGPraktikum ${sources} ${data} "Assimp/assimp/ai_assert.h" "Assimp/assimp/anim.h" "Assimp/assimp/BaseImporter.h" "Assimp/assimp/Bitmap.h" "Assimp/assimp/BlobIOSystem.h" "Assimp/assimp/ByteSwapper.h" "Assimp/assimp/camera.h" "Assimp/assimp/cexport.h" "Assimp/assimp/cfileio.h" "Assimp/assimp/cimport.h" "Assimp/assimp/color4.h" "Assimp/assimp/config.h" "Assimp/assimp/CreateAnimMesh.h" "Assimp/assimp/DefaultIOStream.h" "Assimp/assimp/DefaultIOSystem.h" "Assimp/assimp/DefaultLogger.hpp" "Assimp/assimp/Defines.h" "Assimp/assimp/defs.h" "Assimp/assimp/Exceptional.h" "Assimp/assimp/Exporter.hpp" "Assimp/assimp/fast_atof.h" "Assimp/assimp/GenericProperty.h" "Assimp/assimp/Hash.h" "Assimp/assimp/Importer.hpp" "Assimp/assimp/importerdesc.h" "Assimp/assimp/IOStream.hpp" "Assimp/assimp/IOStreamBuffer.h" "Assimp/assimp/IOSystem.hpp" "Assimp/assimp/irrXMLWrapper.h" "Assimp/assimp/light.h" "Assimp/assimp/LineSplitter.h" "Assimp/assimp/LogAux.h" "Assimp/assimp/Logger.hpp" "Assimp/assimp/LogStream.hpp" "Assimp/assimp/Macros.h" "Assimp/assimp/material.h" "Assimp/assimp/MathFunctions.h" "Assimp/assimp/matrix3x3.h" "Assimp/assimp/matrix4x4.h" "Assimp/assimp/MemoryIOWrapper.h" "Assimp/assimp/mesh.h" "Assimp/assimp/metadata.h" "Assimp/assimp/NullLogger.hpp" "Assimp/assimp/ParsingUtils.h" "Assimp/assimp/pbrmaterial.h" "Assimp/assimp/postprocess.h" "Assimp/assimp/Profiler.h" "Assimp/assimp/ProgressHandler.hpp" "Assimp/assimp/qnan.h" "Assimp/assimp/quaternion.h" "Assimp/assimp/RemoveComments.h" "Assimp/assimp/scene.h" "Assimp/assimp/SceneCombiner.h" "Assimp/assimp/SGSpatialSort.h" "Assimp/assimp/SkeletonMeshBuilder.h" "Assimp/assimp/SmoothingGroups.h" "Assimp/assimp/SpatialSort.h" "Assimp/assimp/StandardShapes.h" "Assimp/assimp/StreamReader.h" "Assimp/assimp/StreamWriter.h" "Assimp/assimp/StringComparison.h" "Assimp/assimp/StringUtils.h" "Assimp/assimp/Subdivision.h" "Assimp/assimp/texture.h" "Assimp/assimp/TinyFormatter.h" "Assimp/assimp/types.h" "Assimp/assimp/vector2.h" "Assimp/assimp/vector3.h" "Assimp/assimp/version.h" "Assimp/assimp/Vertex.h" "Assimp/assimp/XMLTools.h" "Assimp/assimp/Compiler/poppack1.h" "Assimp/assimp/Compiler/pstdint.h" "Assimp/assimp/Compiler/pushpack1.h" "Assimp/assimp/port/AndroidJNI/AndroidJNIIOSystem.h")

# just for example add some compiler flags
target_compile_options(CGPraktikum PUBLIC -std=c++14 -Wall)

set_target_properties( CGPraktikum PROPERTIES
RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}
RUNTIME_OUTPUT_DIRECTORY_DEBUG ${CMAKE_BINARY_DIR}/../
RUNTIME_OUTPUT_DIRECTORY_RELEASE ${CMAKE_BINARY_DIR})


set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${CMAKE_CURRENT_SOURCE_DIR}/cmake)


###############################################################################
## dependencies ###############################################################
###############################################################################

# find external assimp library
set(CMAKE_PREFIX_PATH D:/uni/Module/SS2021/Graphik/Codebasis_Computergrafik_2021/Assimp/)
find_library( ASSIMP_LIBRARY assimp )
find_path( ASSIMP_INCLUDE_DIR assimp/Importer.hpp )

if(ASSIMP_INCLUDE_DIR AND ASSIMP_LIBRARY)
    add_library( ASSIMP INTERFACE IMPORTED )
    set_property( TARGET ASSIMP PROPERTY INTERFACE_INCLUDE_DIRECTORIES ${ASSIMP_INCLUDE_DIR} )
    set_property( TARGET ASSIMP PROPERTY INTERFACE_LINK_LIBRARIES ${ASSIMP_LIBRARY} )
    message( STATUS "!!! ASSIMP found and added to dependencies" )
    target_link_libraries( CGPraktikum PUBLIC ASSIMP )

else()
    message( STATUS "Did not include plugin AssImpMeshImporter due to missing libraries")
endif()



# Parallelisierungsbibliothek mit Intel TBB
find_package( TBB )

if( TBB_FOUND )
    add_library( _TBB INTERFACE IMPORTED )
    set_property( TARGET _TBB PROPERTY INTERFACE_INCLUDE_DIRECTORIES ${TBB_INCLUDE_DIRS} )
    set_property( TARGET _TBB PROPERTY INTERFACE_LINK_LIBRARIES ${TBB_LIBRARIES} )
    message( STATUS "!!! TBB found and added to dependencies" )
    target_link_libraries( CGPraktikum PUBLIC _TBB )

else()
    message( STATUS "!!! TBB not found | TBB OR OpenMP should be installed for exercise 2 and 3" )
endif()

# Parallelisierung mit OpenMP
find_package( OpenMP )
if( OPENMP_FOUND )
     target_link_libraries(CGPraktikum PUBLIC OpenMP::OpenMP_CXX)
    message( STATUS "!!! OpenMP found and added to dependencies" )
else()
    message( STATUS "!!! OpenMP not found | TBB OR OpenMP should be installed for exercise 2 and 3" )
endif()

if( OPENMP_FOUND AND TBB_FOUND )
    message( STATUS "TBB and OpenMP where both added. Please use only one parallelization library in your program code." )
endif()
