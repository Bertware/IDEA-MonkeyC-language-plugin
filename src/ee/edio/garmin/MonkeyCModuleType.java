package ee.edio.garmin;

import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MonkeyCModuleType extends ModuleType<MonkeyCModuleBuilder> {

  public MonkeyCModuleType() {
    super(MCConstants.MODULE_TYPE_ID);
  }

  @NotNull
  public static MonkeyCModuleType getInstance() {
    return (MonkeyCModuleType) ModuleTypeManager.getInstance().findByID(MCConstants.MODULE_TYPE_ID);
  }

  @NotNull
  @Override
  public MonkeyCModuleBuilder createModuleBuilder() {
    return new MonkeyCModuleBuilder();
  }

  @NotNull
  @Override
  public String getName() {
    return "Monkey C Module";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Monkey C modules are used for developing <b>Monkey Co</b> applications.";
  }

  @Override
  public Icon getBigIcon() {
    return MonkeyCIcons.MODULE24;
  } //24

  @Override
  public Icon getNodeIcon(@Deprecated boolean isOpened) {
    return MonkeyCIcons.MODULE16;
  } // 16
}
