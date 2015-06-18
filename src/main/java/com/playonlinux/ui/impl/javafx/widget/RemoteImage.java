/*
 * Copyright (C) 2015 PÂRIS Quentin
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.playonlinux.ui.impl.javafx.widget;

import com.playonlinux.injection.Inject;
import com.playonlinux.injection.Scan;
import com.playonlinux.messages.ParametrableRunnable;
import com.playonlinux.webservice.DownloadManager;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This class has been created to facilitate the integration of remote images inside PlayOnLinux app
 * In general, we should avoid adding such mechanism in the UI implementation
 */
@Scan
public class RemoteImage extends VBox {
    @Inject
    private static DownloadManager downloadManager;

    Logger logger = Logger.getLogger(RemoteImage.class);

    private URL imageUrl;

    public RemoteImage(URL imgeUrl) {
        this.getChildren().add(new ProgressIndicator());
        this.getStyleClass().add("downloadImageWaiting");
        this.imageUrl = imgeUrl;
    }


    public void download() {
        downloadManager.submit(imageUrl, new ParametrableRunnable<byte[]>() {
                    @Override
                    public void run(byte[] parameter) {
                        handleDownloadSuccess(parameter);
                    }
                },
                new ParametrableRunnable<Exception>() {
                    @Override
                    public void run(Exception parameter) {
                        handleError();
                    }
                }
        );
    }

    private void handleError() {
        // FIXME: Handle this case
    }

    public void handleDownloadSuccess(byte[] content) {

        InputStream inputStream = new ByteArrayInputStream(content);
        Image downloadedImage = new Image(inputStream);
        ImageView downloadedImageView = new ImageView(downloadedImage);
        Platform.runLater(() -> {
            this.getChildren().clear();

            double fitWidth;
            double fitHeight;
            if(downloadedImage.getWidth() / downloadedImage.getHeight()
                    > this.getWidth() / this.getHeight()) {
                fitWidth = this.getWidth();
                fitHeight = downloadedImage.getHeight() * (this.getWidth() / downloadedImage.getWidth());
            } else {
                fitHeight = this.getHeight();
                fitWidth = downloadedImage.getWidth() * (this.getHeight() / downloadedImage.getHeight());
            }

            this.getChildren().add(downloadedImageView);
            downloadedImageView.setFitHeight(fitHeight);
            downloadedImageView.setFitWidth(fitWidth);
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.warn(e);
            }
        });

    }
}