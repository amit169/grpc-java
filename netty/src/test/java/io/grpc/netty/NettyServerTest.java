/*
 * Copyright 2016, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *
 *    * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.grpc.netty;

import static com.google.common.truth.Truth.assertThat;

import io.grpc.internal.ServerListener;
import io.grpc.internal.ServerTransport;
import io.grpc.internal.ServerTransportListener;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NettyServerTest {

  @Test
  public void getPort() throws Exception {
    InetSocketAddress addr = new InetSocketAddress(0);
    NettyServer ns = new NettyServer(
        addr,
        NioServerSocketChannel.class,
        null, // no boss group
        null, // no event group
        new ProtocolNegotiators.PlaintextNegotiator(),
        1, // ignore
        1, // ignore
        1, // ignore
        1, // ignore
        1, // ignore
        1); // ignore
    ns.start(new ServerListener() {
      @Override
      public ServerTransportListener transportCreated(ServerTransport transport) {
        return null;
      }

      @Override
      public void serverShutdown() {}
    });

    // Check that we got an actual port.
    assertThat(ns.getPort()).isGreaterThan(0);

    // Cleanup
    ns.shutdown();
  }

  @Test
  public void getPort_notStarted() throws Exception {
    InetSocketAddress addr = new InetSocketAddress(0);
    NettyServer ns = new NettyServer(
        addr,
        NioServerSocketChannel.class,
        null, // no boss group
        null, // no event group
        new ProtocolNegotiators.PlaintextNegotiator(),
        1, // ignore
        1, // ignore
        1, // ignore
        1, // ignore
        1, // ignore
        1); // ignore

    assertThat(ns.getPort()).isEqualTo(-1);
  }
}