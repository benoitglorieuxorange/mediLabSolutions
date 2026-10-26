

## Générer une clé pour JWT_SECRET
echo "export JWT_SECRET=$(openssl rand -base64 48)" >> ~/.bashrc && source ~/.bashrc