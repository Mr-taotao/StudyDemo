#cmake最小版本
cmake_minimum_required(VERSION 3.4.1)

add_library( # 设置so文件名称.
             native-jni
             # 设置这个so文件为共享.
             SHARED
             # 设置 c文件源码位置.
             src/main/jni/native-lib.cpp
             src/main/jni/test.cpp)


find_library( # Sets the name of the path variable.
              log-lib
              log )


target_link_libraries( # 制定目标库.
                       native-jni
                       ${log-lib} )