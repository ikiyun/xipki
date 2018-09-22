/*
 *
 * Copyright (c) 2013 - 2018 Lijun Liao
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

package org.xipki.ca.server.mgmt.shell.cert;

import java.security.cert.X509CRL;

import org.apache.karaf.shell.api.action.Completion;
import org.apache.karaf.shell.api.action.Option;
import org.xipki.ca.server.mgmt.api.CaEntry;
import org.xipki.ca.server.mgmt.shell.CaAction;
import org.xipki.ca.server.mgmt.shell.completer.CaNameCompleter;
import org.xipki.shell.CmdFailure;
import org.xipki.shell.completer.DerPemCompleter;

/**
 * TODO.
 * @author Lijun Liao
 * @since 2.0.0
 */

public abstract class CrlAction extends CaAction {

  @Option(name = "--ca", required = true, description = "CA name")
  @Completion(CaNameCompleter.class)
  protected String caName;

  @Option(name = "--outform", description = "output format of the CRL")
  @Completion(DerPemCompleter.class)
  protected String outform = "der";

  protected abstract X509CRL retrieveCrl() throws Exception;

  @Override
  protected Object execute0() throws Exception {
    CaEntry ca = caManager.getCa(caName);
    if (ca == null) {
      throw new CmdFailure("CA " + caName + " not available");
    }

    X509CRL crl = null;
    try {
      crl = retrieveCrl();
    } catch (Exception ex) {
      throw new CmdFailure("received no CRL from server: " + ex.getMessage());
    }

    if (crl == null) {
      throw new CmdFailure("received no CRL from server");
    }

    String outFile = getOutFile();
    if (outFile != null) {
      saveVerbose("saved CRL to file", outFile, encodeCrl(crl.getEncoded(), outform));
    }
    return null;
  }

  protected abstract String getOutFile();

}
