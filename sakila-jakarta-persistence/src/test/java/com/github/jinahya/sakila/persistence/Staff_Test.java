package com.github.jinahya.sakila.persistence;

import com.github.jinahya.sakila.persistence.util.SecurityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

class Staff_Test
        extends _BaseEntityTest<Staff, Integer> {

    private static final Logger log = getLogger(lookup().lookupClass());

    private static byte[] getPictureBytes() throws URISyntaxException, IOException {
        final var uri = Objects.requireNonNull(Staff_Test.class.getResource("/picture.png")).toURI();
        return Files.readAllBytes(Path.of(uri));
    }

    private static File getPictureFile(final File dir) throws URISyntaxException, IOException {
        Objects.requireNonNull(dir, "dir is null");
        final var file = File.createTempFile("tmp", "tmp", dir);
        Files.write(file.toPath(), getPictureBytes());
        return file;
    }

    private static Path getPicturePath(final Path dir) throws URISyntaxException, IOException {
        Objects.requireNonNull(dir, "dir is null");
        final var path = Files.createTempFile(dir, null, null);
        Files.write(path, getPictureBytes());
        return path;
    }

    Staff_Test() {
        super(Staff.class, Integer.class);
    }

    @DisplayName("getPictureWritingTo(OutputStream)")
    @Nested
    class GetPictureWritingToStreamTest {

        @Test
        void _NullPointerException_Null() {
            // GIVEN
            final var instance = newEntityInstance();
            // WHEN/THEN
            assertThatThrownBy(() -> instance.getPictureWritingTo((OutputStream) null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void __NotNull() throws Exception {
            // GIVEN
            final var instance = newEntitySpy();
            final var pictureBytes = getPictureBytes();
            when(instance.getPicture()).thenReturn(pictureBytes);
            clearInvocations(instance); // EclipseLink
            final var stream = mock(OutputStream.class);
            // WHEN
            assertDoesNotThrow(() -> instance.getPictureWritingTo(stream));
            // THEN
            verify(instance, times(1)).getPicture();
            verify(stream, times(1)).write(pictureBytes);
        }
    }

    @DisplayName("getPictureWritingTo(File)")
    @Nested
    class GetPictureWritingToFileTest {

        @Test
        void _IllegalStateException_Null(@TempDir final File dir) throws IOException {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getPicture()).thenReturn(null);
            final var file = File.createTempFile("tmp", "tmp", dir);
            // WHEN/THEN
            assertThatThrownBy(() -> instance.getPictureWritingTo(file))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        void __NotNull(@TempDir final File dir) throws URISyntaxException, IOException {
            // GIVEN
            final var instance = newEntitySpy();
            final var pictureBytes = getPictureBytes();
            when(instance.getPicture()).thenReturn(pictureBytes);
            final var file = File.createTempFile("tmp", "tmp", dir);
            // WHEN
            assertDoesNotThrow(() -> instance.getPictureWritingTo(file));
            // WHEN
            assertThat(file).hasSize(pictureBytes.length);
        }
    }

    @DisplayName("getPictureWritingTo(WritableByteChannel)")
    @Nested
    class GetPictureWritingToChannelTest {

        @Test
        void _NullPointerException_Null() {
            // GIVEN
            final var instance = newEntityInstance();
            // WHEN/THEN
            assertThatThrownBy(() -> instance.getPictureWritingTo((WritableByteChannel) null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void __NotNull() throws Exception {
            // GIVEN
            final var instance = newEntitySpy();
            final var pictureBytes = getPictureBytes();
            when(instance.getPicture()).thenReturn(pictureBytes);
            clearInvocations(instance); // EclipseLink
            final var channel = mock(WritableByteChannel.class);
            when(channel.write(notNull())).thenAnswer(i -> {
                final var buffer = i.getArgument(0, ByteBuffer.class);
                final var remaining = buffer.remaining();
                buffer.position(buffer.limit());
                return remaining;
            });
            // WHEN
            assertDoesNotThrow(() -> instance.getPictureWritingTo(channel));
            // THEN
            verify(instance, times(1)).getPicture();
            verify(channel, atLeast(1)).write(notNull());
        }
    }

    @DisplayName("getPictureWritingTo(Path)")
    @Nested
    class GetPictureWritingToPathTest {

        @Test
        void _IllegalStateException_Null(@TempDir final Path dir) throws IOException {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getPicture()).thenReturn(null);
            final var path = Files.createTempFile(dir, null, null);
            // WHEN/THEN
            assertThatThrownBy(() -> instance.getPictureWritingTo(path))
                    .isInstanceOf(IllegalStateException.class);
        }

        @Test
        void __NotNull(@TempDir final Path dir) throws URISyntaxException, IOException {
            // GIVEN
            final var instance = newEntitySpy();
            final var pictureBytes = getPictureBytes();
            when(instance.getPicture()).thenReturn(pictureBytes);
            final var path = Files.createTempFile(dir, null, null);
            // WHEN
            assertDoesNotThrow(() -> instance.getPictureWritingTo(path));
            // WHEN
            assertThat(path).hasSize(pictureBytes.length);
        }
    }

    @DisplayName("setPictureReadingFrom(InputStream)")
    @Nested
    class SetPictureReadingFromInputStreamTest {

        @Test
        void __(@TempDir final File dir) throws Exception {
            // GIVEN
            final var instance = newEntitySpy();
            final var file = getPictureFile(dir);
            // WHEN
            try (final var stream = new FileInputStream(file)) {
                instance.setPictureReadingFrom(stream);
            }
            // THEN
            verify(instance, times(1)).setPicture(notNull());
        }
    }

    @DisplayName("setPictureReadingFrom(File)")
    @Nested
    class SetPictureReadingFromFileTest {

        @Test
        void __(@TempDir final File dir) throws Exception {
            // GIVEN
            final var instance = newEntitySpy();
            final var file = getPictureFile(dir);
            // WHEN
            instance.setPictureReadingFrom(file);
            // THEN
            verify(instance, times(1)).setPictureReadingFrom(notNull(InputStream.class));
        }
    }

    @DisplayName("setPictureReadingFrom(Path)")
    @Nested
    class SetPictureReadingFromPathTest {

        @Test
        void __(@TempDir final Path dir) throws Exception {
            // GIVEN
            final var instance = newEntitySpy();
            final var path = getPicturePath(dir);
            // WHEN
            instance.setPictureReadingFrom(path);
            // THEN
            verify(instance, times(1)).setPictureReadingFrom(notNull(InputStream.class));
        }
    }

    @DisplayName("activate()Staff")
    @Nested
    class ActivateTest {

        @Test
        void __() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var result = instance.activate();
            // THEN
            verify(instance, times(1)).setActive(Boolean.TRUE);
            assertThat(result).isSameAs(instance);
        }
    }

    @DisplayName("deactivate()Staff")
    @Nested
    class DeactivateTest {

        @Test
        void __() {
            // GIVEN
            final var instance = newEntitySpy();
            // WHEN
            final var result = instance.deactivate();
            // THEN
            verify(instance, times(1)).setActive(Boolean.FALSE);
            assertThat(result).isSameAs(instance);
        }
    }

    @DisplayName("signIn(byte[])")
    @Nested
    class SignInTest {

        @DisplayName("signIn(null) -> does not throw")
        @Test
        void _DoesNotThrow_Null() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getPassword()).thenReturn(null);
            // WHEN/THEN
            assertThatCode(
                    () -> instance.signIn(null)
            ).doesNotThrowAnyException();
        }

        @DisplayName("signIn(byte[0]) -> IllegalArgumentException")
        @Test
        void _IllegalArgumentException_Empty() {
            // GIVEN
            final var instance = newEntityInstance();
            // WHEN/THEN
            assertThatThrownBy(
                    () -> instance.signIn(new byte[0])
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("getPassword()null <- signIn(null)")
        @Test
        void _DoesNotThrow_NullNull() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getPassword()).thenReturn(null);
            // WHEN/THEN
            assertThatCode(
                    () -> instance.signIn(null)
            ).doesNotThrowAnyException();
        }

        @DisplayName("getPassword()!null <- signIn(null) -> IllegalStateException")
        @Test
        void _IllegalArgumentException_NullNotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getPassword()).thenReturn("");
            // WHEN/THEN
            assertThatThrownBy(
                    () -> instance.signIn(null)
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("getPassword()!null <- signIn(wrong) -> IllegalArgumentException")
        @Test
        void _IllegalArgumentException_WrongNotNull() {
            // GIVEN
            final var instance = newEntitySpy();
            when(instance.getPassword()).thenReturn("");
            // WHEN/THEN
            assertThatThrownBy(
                    () -> instance.signIn(new byte[0])
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("getPassword()!null <- signIn(SHA1)")
        @Test
        void _SHA1_() {
            // GIVEN
            final var instance = newEntitySpy();
            final var clientPassword = new byte[1];
            final var password = SecurityUtils.sha1(clientPassword);
            when(instance.getPassword()).thenReturn(password);
            // WHEN/THEN
            assertThatCode(
                    () -> instance.signIn(clientPassword)
            ).doesNotThrowAnyException();
        }

        @DisplayName("getPassword()!null <- signIn(SHA2)")
        @Test
        void _SHA2_() {
            // GIVEN
            final var instance = newEntitySpy();
            final var clientPassword = new byte[1];
            final var password = SecurityUtils.sha2(clientPassword);
            when(instance.getPassword()).thenReturn(password);
            // WHEN/THEN
            assertThatCode(
                    () -> instance.signIn(clientPassword)
            ).doesNotThrowAnyException();
        }
    }

    @DisplayName("changePassword(byte[], byte[])")
    @Nested
    class ChangePasswordTest {
        // TODO: add testcases!
    }
}
