/*
 * Copyright (C) 2015 PÂRIS Quentin
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.playonlinux.dto;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

import com.playonlinux.dto.web.ScriptDTO;

public class ScriptDTOTest {

    private ScriptDTO scriptDTO;

    @Before
    public void setUp() throws MalformedURLException {
        this.scriptDTO = new ScriptDTO.Builder()
                .withName("Name")
                .withId(13)
                .build();
    }
    @Test
    public void testScriptDTO_CreateDTO_nameIsPopulated() throws Exception {
        assertEquals("Name", scriptDTO.getName());
    }

    @Test
    public void testScriptDTO_CreateDTO_iconIsPopulated() throws Exception {
        assertEquals(13, scriptDTO.getId());
    }
}