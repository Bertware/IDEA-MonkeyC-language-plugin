package com.github.bertware.monkeyc_intellij.project.dom.manifest;

import java.util.List;

public interface Permissions extends ManifestDomElement {
  List<UsesPermission> getUsesPermissions();
}