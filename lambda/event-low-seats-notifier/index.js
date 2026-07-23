const { S3Client, PutObjectCommand } = require("@aws-sdk/client-s3");

const s3 = new S3Client({});
const BUCKET = process.env.NOTIFICATIONS_BUCKET;

exports.handler = async (event) => {
  console.log("Received event:", JSON.stringify(event));

  const { eventId, seatsAvailable, threshold } = event;
  if (!eventId || seatsAvailable === undefined) {
    console.error("Missing required fields:", JSON.stringify(event));
    return { statusCode: 400, body: "Missing eventId or seatsAvailable" };
  }

  const timestamp = new Date().toISOString();
  const notification = { eventId, timestamp, seatsAvailable, threshold: threshold ?? null };
  const key = `notifications/${eventId}/${timestamp}.json`;

  await s3.send(new PutObjectCommand({
    Bucket: BUCKET,
    Key: key,
    Body: JSON.stringify(notification, null, 2),
    ContentType: "application/json",
  }));

  console.log(`Wrote notification to s3://${BUCKET}/${key}`);
  return { statusCode: 200, body: JSON.stringify(notification) };
};
