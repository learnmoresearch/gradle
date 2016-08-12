/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.internal.resolve.result;

import org.gradle.api.Nullable;
import org.gradle.api.artifacts.component.ComponentArtifactIdentifier;
import org.gradle.internal.resolve.ArtifactResolveException;

import java.io.File;

public interface BuildableArtifactResolveResult extends ResolveResult, BuildableTypedResolveResult<File, ArtifactResolveException>, ResourceAwareResolveResult {
    /**
     * Returns the resolve failure, if any.
     */
    @Nullable
    ArtifactResolveException getFailure();

    /**
     * @throws ArtifactResolveException If the resolution was unsuccessful.
     */
    File getResult() throws ArtifactResolveException;

    /**
     * Marks the artifact as resolved to the given file.
     */
    void resolved(File file);

    /**
     * Marks the resolve as failed with the given exception.
     */
    void failed(ArtifactResolveException failure);

    /**
     * Marks the artifact as not found.
     */
    void notFound(ComponentArtifactIdentifier artifact);
}
