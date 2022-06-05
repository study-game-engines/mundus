/*
 * Copyright (c) 2016. See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mbrlabs.mundus.commons.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mbrlabs.mundus.commons.shaders.LightShader;

/**
 * @author Marcus Brummer
 * @version 23-11-2015
 */
public class ShaderUtils {

    public static final int MAX_POINT_LIGHTS = 4;
    public static final int MAX_SPOT_LIGHTS = 4;

    protected static final String LIGHT_SHADER_PREFIX = "com/mbrlabs/mundus/commons/shaders/light.glsl";

    /**
     * Compiles and links shader.
     *
     * @param vertexShader
     *            path to vertex shader
     * @param fragmentShader
     *            path to fragment shader
     * @param shader
     *            the shader to compile a program for
     *
     * @return compiled shader program
     */
    public static ShaderProgram compile(String vertexShader, String fragmentShader, Shader shader) {
        String vert;
        String frag;
        String fragPrefix = "";

        if (Gdx.app.getType() == Application.ApplicationType.WebGL) {
            vert = Gdx.files.internal(vertexShader).readString();
            frag = Gdx.files.internal(fragmentShader).readString();
        } else {
            vert = Gdx.files.classpath(vertexShader).readString();
            frag = Gdx.files.classpath(fragmentShader).readString();
        }

        if (shader instanceof LightShader) {
            fragPrefix = Gdx.files.internal(LIGHT_SHADER_PREFIX).readString();
        }

        ShaderProgram program = new ShaderProgram(vert, fragPrefix + frag);
        if (!program.isCompiled()) {
            throw new GdxRuntimeException(program.getLog());
        }

        return program;
    }

}
