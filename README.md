# receipt-processor-backend

# Import an receipt processor image (from a .tar file):

docker load -i receiptprocessor-image.tar

# Verify the Image

docker images

# Run the Container

docker run -p 8080:8080 receiptprocessor