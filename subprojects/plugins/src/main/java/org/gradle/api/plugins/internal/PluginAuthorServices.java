/*
 * Copyright 2019 the original author or authors.
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
package org.gradle.api.plugins.internal;

import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.component.SoftwareComponentFactory;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.JvmEcosystemUtilities;
import org.gradle.api.plugins.internal.support.DefaultJvmEcosystemUtilities;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.internal.service.ServiceRegistration;
import org.gradle.internal.service.scopes.AbstractPluginServiceRegistry;

/**
 * Registers services that can be used by plugin authors to develop their plugins.
 */
public class PluginAuthorServices extends AbstractPluginServiceRegistry {
    @Override
    public void registerGlobalServices(ServiceRegistration registration) {
        registration.addProvider(new GlobalScopeServices());
    }

    @Override
    public void registerProjectServices(ServiceRegistration registration) {
        registration.addProvider(new ProjectScopeServices());
    }

    private static class GlobalScopeServices {
        SoftwareComponentFactory createSoftwareComponentFactory(Instantiator instantiator) {
            return new DefaultSoftwareComponentFactory(instantiator);
        }
    }

    private static class ProjectScopeServices {
        JvmEcosystemUtilities createJvmEcosystemUtilities(ConfigurationContainer configurations,
                                                          ObjectFactory objectFactory,
                                                          TaskContainer tasks) {
            return new DefaultJvmEcosystemUtilities(configurations, objectFactory, tasks);
        }
    }
}
