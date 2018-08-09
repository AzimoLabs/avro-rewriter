Map applicationConfigFiles = [
        "[dataflow] tcc-payment-rewriter": "./config/tcc-payment-rewriter.conf",
        "[dataflow] bbva-payout-rewriter": "./config/bbva-payout-rewriter.conf"
]

googleCloudPlatformPipeline(
        nexusReleasesRepo: "maven-releases",
        nexusSnapshotsRepo: "maven-snapshots",
        artifactGroup: "com.azimo",
        applicationConfigFiles: applicationConfigFiles
)
