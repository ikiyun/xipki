/*
 *
 * Copyright (c) 2013 - 2017 Lijun Liao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xipki.security.shell.p11;

import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.xipki.security.pkcs11.P11ObjectIdentifier;
import org.xipki.security.pkcs11.P11Slot;

/**
 * @author Lijun Liao
 * @since 2.0.0
 */

@Command(scope = "xi", name = "delete-key-p11",
        description = "delete key and cert in PKCS#11 device")
@Service
public class P11IdentityDeleteCmd extends P11SecurityAction {

    @Option(name = "--force", aliases = "-f",
            description = "remove identifies without prompt")
    private Boolean force = Boolean.FALSE;

    @Override
    protected Object execute0() throws Exception {
        P11Slot slot = getSlot();
        P11ObjectIdentifier objIdentifier = getObjectIdentifier();
        if (objIdentifier == null) {
            println("identity to be deleted does not exist");
            return null;
        }

        if (force || confirm("Do you want to remove the identity " + objIdentifier, 3)) {
            slot.removeIdentity(objIdentifier);
            println("deleted identity " + objIdentifier);
        }
        return null;
    }

}
