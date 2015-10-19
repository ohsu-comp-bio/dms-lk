FROM bconn/labkey-standalone
EXPOSE 8080

RUN TOMCAT_DIR=/usr/local/tomcat
RUN LK_DIR=/usr/local/labkey
RUN LABKEY_USER=labkey

#install custom modules
RUN mkdir -p ${LK_DIR}/externalModules
RUN cd ${LK_DIR}/externalModules
#private repo, unsure how to handle credentials.  not necessary to have this module for now.
#RUN svn export --no-auth-cache https://github.com/Intel-HSS/CCC_CF_Client/trunk/build/ccc_client.module
RUN svn export --no-auth-cache https://github.com/ohsu-computational-biology/dms-lk/trunk/modules/build/beataml.module

#now download latest build, which includes additional modules 
RUN mkdir -p ${LK_DIR}/lk_download
RUN cd ${LK_DIR}/lk_download
RUN wget -r --trust-server-names --no-check-certificate http://teamcity.labkey.org/guestAuth/repository/download/LabkeyDISCVR152_Installers/.lastSuccessful/onprc/LabKey15.2DISCVR-{build.number}-onprc-bin.tar.gz
RUN GZ=$(ls -tr | grep '^LabKey.*\.gz$' | tail -n -1)
RUN echo "Unzipping $GZ"
RUN gunzip $GZ
RUN TAR=`echo $GZ | sed -e "s/.gz$//"`
RUN echo "TAR: $TAR"
RUN tar -xf $TAR
RUN DIR=`echo $TAR | sed -e "s/.tar$//"`
RUN echo "DIR: $DIR"
RUN cd $DIR
RUN bash manual-upgrade.sh -u $LABKEY_USER -c "$TOMCAT_DIR" -l "$LK_DIR"
RUN cd ${LK_DIR}
RUN rm -Rf ${LK_DIR}/lk_download

CMD bash /labkey/bin/start_labkey.sh
