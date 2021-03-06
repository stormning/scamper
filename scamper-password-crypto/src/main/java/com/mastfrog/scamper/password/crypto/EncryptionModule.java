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
package com.mastfrog.scamper.password.crypto;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mastfrog.scamper.MessageTypeRegistry;
import com.mastfrog.scamper.codec.MessageCodec;
import com.mastfrog.scamper.codec.RawMessageCodec;
import com.mastfrog.scamper.compression.CompressionModule;
import com.mastfrog.settings.Settings;

/**
 *
 * @author Tim Boudreau
 */
public class EncryptionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessageCodec.class).toProvider(EncryptingCodecProvider.class);
    }

    @Singleton
    private static class EncryptingCodecProvider implements Provider<MessageCodec> {
        private final EncryptingCodec codec;

        @Inject
        EncryptingCodecProvider(RawMessageCodec raw, MessageTypeRegistry reg, Settings settings) {
            MessageCodec compress = CompressionModule.newCompressingCodec(raw, reg, settings);
            Encrypter enc = new Encrypter(settings);
            codec = new EncryptingCodec(compress, reg, enc);
        }

        @Override
        public MessageCodec get() {
            return codec;
        }
    }
}
