/*
 * Copyright (c) 2015 Tim Boudreau
 *
 * This file is part of Scamper.
 *
 * Scamper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mastfrog.scamper.compression;

import com.google.inject.AbstractModule;
import com.mastfrog.scamper.MessageTypeRegistry;
import com.mastfrog.scamper.codec.MessageCodec;
import com.mastfrog.settings.Settings;

/**
 *
 * @author Tim Boudreau
 */
public class CompressionModule extends AbstractModule {

    public static final String SETTINGS_KEY_COMPRESSION_THRESHOLD = "sctp.compress.threshold";
    public static final int DEFAULT_COMPRESSION_THRESHOLD = 256;
    public static final String SETTINGS_KEY_GZIP_LEVEL = "sctp.compress.gzip.level";
    public static final int DEFAULT_GZIP_COMPRESSION_LEVEL = 9;

    @Override
    protected void configure() {
        bind(MessageCodec.class).to(AutoCompressCodec.class);
    }

    public static MessageCodec newCompressingCodec(MessageCodec raw, MessageTypeRegistry reg, Settings settings) {
        return new CompressingCodec(raw, reg, settings);
    }
}
