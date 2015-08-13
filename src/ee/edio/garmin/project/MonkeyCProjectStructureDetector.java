package ee.edio.garmin.project;

import com.intellij.ide.util.importProject.ModuleDescriptor;
import com.intellij.ide.util.importProject.ProjectDescriptor;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.importSources.DetectedProjectRoot;
import com.intellij.ide.util.projectWizard.importSources.DetectedSourceRoot;
import com.intellij.ide.util.projectWizard.importSources.ProjectFromSourcesBuilder;
import com.intellij.ide.util.projectWizard.importSources.ProjectStructureDetector;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.containers.ContainerUtil;
import ee.edio.garmin.MonkeyCModuleType;
import ee.edio.garmin.sdk.MonkeyCSdkType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class MonkeyCProjectStructureDetector extends ProjectStructureDetector {
  @NotNull
  @Override
  public DirectoryProcessingResult detectRoots(@NotNull File dir,
                                               @NotNull File[] children,
                                               @NotNull File base,
                                               @NotNull List<DetectedProjectRoot> result) {
    Pattern pattern = Pattern.compile(".*\\.mc");
    List<File> filesByMask = FileUtil.findFilesByMask(pattern, base);
    if (!filesByMask.isEmpty()) {
      result.add(new DetectedProjectRoot(dir) {
        @NotNull
        @Override
        public String getRootTypeName() {
          return "MonkeyC";
        }
      });
    }
    return DirectoryProcessingResult.SKIP_CHILDREN;
  }

  @Override
  public void setupProjectStructure(@NotNull Collection<DetectedProjectRoot> roots,
                                    @NotNull ProjectDescriptor projectDescriptor,
                                    @NotNull ProjectFromSourcesBuilder builder) {
    if (!roots.isEmpty() && !builder.hasRootsFromOtherDetectors(this)) {
      List<ModuleDescriptor> modules = projectDescriptor.getModules();
      if (modules.isEmpty()) {
        modules = new ArrayList<>();
        for (DetectedProjectRoot root : roots) {
          modules.add(new ModuleDescriptor(root.getDirectory(), MonkeyCModuleType.getInstance(), ContainerUtil.<DetectedSourceRoot>emptyList()));
        }
        projectDescriptor.setModules(modules);
      }
    }
  }

  @NotNull
  @Override
  public List<ModuleWizardStep> createWizardSteps(@NotNull ProjectFromSourcesBuilder builder, ProjectDescriptor projectDescriptor, Icon stepIcon) {
    ProjectJdkForModuleStep projectJdkForModuleStep = new ProjectJdkForModuleStep(builder.getContext(), MonkeyCSdkType.getInstance());
    return Collections.<ModuleWizardStep>singletonList(projectJdkForModuleStep);
  }
}
